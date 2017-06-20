package com.example.jhargreaves4.testedittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addInternalTextWatcher();
    }

    private void addInternalTextWatcher() {

        super.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("");
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
