package com.example.jhargreaves4.testedittext;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import android.widget.EditText;

public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addInternalTextWatcher();
    }

    private void addInternalTextWatcher() {

        super.addTextChangedListener(new TextWatcher()
        {
            String mPreviousText;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mPreviousText = s.toString();

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().substring(start, start + count);
                String oldText = mPreviousText.substring(start, start + before);
                System.out.print("onTextChanged");
            }
            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("");
            }
        });
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new InternalInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class InternalInputConnection extends InputConnectionWrapper {

        public InternalInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean commitContent(InputContentInfo inputContentInfo, int flags, Bundle opts) {
            return super.commitContent(inputContentInfo, flags, opts);
        }

        @Override
        public void setTarget(InputConnection target) {
            super.setTarget(target);
        }

        @Override
        public CharSequence getTextBeforeCursor(int n, int flags) {
            return super.getTextBeforeCursor(n, flags);
        }

        @Override
        public CharSequence getTextAfterCursor(int n, int flags) {
            return super.getTextAfterCursor(n, flags);
        }

        @Override
        public CharSequence getSelectedText(int flags) {
            return super.getSelectedText(flags);
        }

        @Override
        public int getCursorCapsMode(int reqModes) {
            return super.getCursorCapsMode(reqModes);
        }

        @Override
        public ExtractedText getExtractedText(ExtractedTextRequest request, int flags) {
            return super.getExtractedText(request, flags);
        }

        @Override
        public boolean deleteSurroundingTextInCodePoints(int beforeLength, int afterLength) {
            return super.deleteSurroundingTextInCodePoints(beforeLength, afterLength);
        }

        @Override
        public boolean setComposingText(CharSequence text, int newCursorPosition) {
            return super.setComposingText(text, newCursorPosition);
        }

        @Override
        public boolean setComposingRegion(int start, int end) {
            return super.setComposingRegion(start, end);
        }

        @Override
        public boolean finishComposingText() {
            return super.finishComposingText();
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean commitCompletion(CompletionInfo text) {
            return super.commitCompletion(text);
        }

        @Override
        public boolean commitCorrection(CorrectionInfo correctionInfo) {
            return super.commitCorrection(correctionInfo);
        }

        @Override
        public boolean setSelection(int start, int end) {
            return super.setSelection(start, end);
        }

        @Override
        public boolean performEditorAction(int editorAction) {
            return super.performEditorAction(editorAction);
        }

        @Override
        public boolean performContextMenuAction(int id) {
            return super.performContextMenuAction(id);
        }

        @Override
        public boolean beginBatchEdit() {
            return super.beginBatchEdit();
        }

        @Override
        public boolean endBatchEdit() {
            return super.endBatchEdit();
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean clearMetaKeyStates(int states) {
            return super.clearMetaKeyStates(states);
        }

        @Override
        public boolean reportFullscreenMode(boolean enabled) {
            return super.reportFullscreenMode(enabled);
        }

        @Override
        public boolean performPrivateCommand(String action, Bundle data) {
            return super.performPrivateCommand(action, data);
        }

        @Override
        public boolean requestCursorUpdates(int cursorUpdateMode) {
            return super.requestCursorUpdates(cursorUpdateMode);
        }

        @Override
        public Handler getHandler() {
            return super.getHandler();
        }

        @Override
        public void closeConnection() {
            super.closeConnection();
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // deleteSurroundingText(1, 0) will be called for backspace for 'committed' text
            // We want to catch the case whereby TextWatcher won't work. I.E. an empty textbox,
            // or cursor at is at the beginning of EditText and no text deleted.
            // The rest of the cases we will deal with with TextWatcher
            System.out.println("Selection: " + CustomEditText.this.getSelectionStart());
            if (beforeLength == 1 && afterLength == 0 && CustomEditText.this.getSelectionStart() == 0)
            {
                System.out.println("Delete on empty EditText");
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

}
