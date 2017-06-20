package com.example.jhargreaves4.testedittext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class CustomEditText extends EditText {
    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context) {
        super(context);
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
            // We want to catch the case whereby TextWatcher won't work. I.E. an empty textbox
            if (beforeLength == 1 && afterLength == 0 && CustomEditText.this.getText().length() == 0)
            {
                System.out.println("Delete on empty EditText");
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

}
