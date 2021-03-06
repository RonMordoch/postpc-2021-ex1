package android.exercise.mini.interactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity
{

    private boolean isEditing = false;
    private FloatingActionButton fabStartEdit, fabEditDone;
    private TextView textViewTitle;
    private EditText editTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_title);

        // find all views
        fabStartEdit = findViewById(R.id.fab_start_edit);
        fabEditDone = findViewById(R.id.fab_edit_done);
        textViewTitle = findViewById(R.id.textViewPageTitle);
        editTextTitle = findViewById(R.id.editTextPageTitle);

        // setup - start from static title with "edit" button
        fabStartEdit.setVisibility(View.VISIBLE);
        fabEditDone.setVisibility(View.GONE);
        textViewTitle.setText(R.string.page_title_here);
        textViewTitle.setVisibility(View.VISIBLE);
        editTextTitle.setText(R.string.page_title_here);
        editTextTitle.setVisibility(View.GONE);

        // handle clicks on "start edit"
        fabStartEdit.setOnClickListener(v -> {
            isEditing = true;
            // animate out the "start edit" FAB and animate in the "done edit" FAB
            fadeOutAnimation(fabStartEdit);
            fadeInAnimation(fabEditDone);
            // hide the static title and show the editable title
            textViewTitle.setVisibility(View.GONE);
            editTextTitle.setVisibility(View.VISIBLE);
            // make sure the editable title's text is the same as the static one
            editTextTitle.setText(textViewTitle.getText());
            // make the keyboard to open with the edit-text focused
            showSoftKeyboard(editTextTitle);
        });

        // handle clicks on "done edit"
        fabEditDone.setOnClickListener(v -> {
            isEditing = false;
            // animate out the "done edit" FAB and animate in the "start edit" FAB
            fadeOutAnimation(fabEditDone);
            fadeInAnimation(fabStartEdit);
            // set the user's input txt from the edit-text as the static text-view text
            textViewTitle.setText(editTextTitle.getText());
            // show the static title (text-view) and hide the editable title (edit-text)
            textViewTitle.setVisibility(View.VISIBLE);
            editTextTitle.setVisibility(View.GONE);
            // close the keyboard
            hideSoftKeyboard(editTextTitle);
        });
    }

    @Override
    public void onBackPressed()
    {
        // BACK button was clicked

        if (isEditing)
        {
            isEditing = false;
            editTextTitle.setVisibility(View.GONE); // hide the edit-text
            textViewTitle.setVisibility(View.VISIBLE); // show the static text-view with previous text
            // animate out the "done-edit" FAB and animate in the "start-edit" FAB
            fadeOutAnimation(fabEditDone);
            fadeInAnimation(fabStartEdit);
        }
        else // exit screen
        {
            super.onBackPressed();
        }
    }


    public void showSoftKeyboard(View view)
    {
        if (view.requestFocus())
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideSoftKeyboard(View view)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void fadeInAnimation(View view)
    {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.animate()
                .alpha(1f)
                .setDuration(400L)
                .start();
    }

    public void fadeOutAnimation(View view)
    {
        view.animate()
                .alpha(0f)
                .setStartDelay(100L)
                .setDuration(400L)
                .withEndAction
                    (
                    () -> view.setVisibility(View.INVISIBLE)
                    ).start();
    }

}