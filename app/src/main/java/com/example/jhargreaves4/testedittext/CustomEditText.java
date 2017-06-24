package com.example.jhargreaves4.testedittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;
import android.widget.Toast;

public class CustomEditText extends EditText {

    public static final String NewLineRawValue = "\n";
    public static final String BackspaceKeyValue = "Backspace";
    public static final String EnterKeyValue = "Enter";


    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new InternalInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class InternalInputConnection extends InputConnectionWrapper {
        int mPreviousSelectionStart;
        int mPreviousSelectionEnd;
        // Can be multiple commits in a batch edit
        String mCommittedText;
        String mComposedText;

        public InternalInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean setComposingText(CharSequence text, int newCursorPosition) {
            mComposedText = text.toString();
            return super.setComposingText(text, newCursorPosition);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            mCommittedText = text.toString();
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean beginBatchEdit() {
            mPreviousSelectionStart = CustomEditText.this.getSelectionStart();
            mPreviousSelectionEnd = CustomEditText.this.getSelectionEnd();
            mCommittedText = null;
            mComposedText = null;
            return super.beginBatchEdit();
        }

        @Override
        public boolean endBatchEdit() {
            String key;
            int selectionStart = CustomEditText.this.getSelectionStart();
            if (mCommittedText == null) {
                if ((noPreviousSelection() && selectionStart < mPreviousSelectionStart)
                    || (!noPreviousSelection() && selectionStart == mPreviousSelectionStart)
                    || (mPreviousSelectionStart == 0 && selectionStart == 0)) {
                    key = BackspaceKeyValue;
                } else {
                    char enteredChar = CustomEditText.this.getText().charAt(selectionStart - 1);
                    key = String.valueOf(enteredChar);
                }
            }
            else {
                // If during the batch edit we have both committed & composed
                // then the IME may have committed a space to start a new word,
                // before creating a new composing region with the user's keyboard input
                // We want to take the composed input in this case as that's the key
                // they actually pressed
                if (mComposedText != null) {
                    key = mComposedText;
                } else {
                    key = keyValueFromString(mCommittedText);
                }
            }
            key = keyValueFromString(key);
            Toast.makeText(CustomEditText.this.getContext(), key, Toast.LENGTH_SHORT).show();
            return super.endBatchEdit();
        }

        private boolean noPreviousSelection() {
            return mPreviousSelectionStart == mPreviousSelectionEnd;
        }

        private String keyValueFromString(final String key) {
            String returnValue;
            switch (key) {
                case "": returnValue = BackspaceKeyValue;
                    break;
                case NewLineRawValue: returnValue = EnterKeyValue;
                    break;
                default:
                    returnValue = key;
                    break;
            }
            return returnValue;
        }
    }

}
