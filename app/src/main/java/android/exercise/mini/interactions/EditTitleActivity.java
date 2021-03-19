package android.exercise.mini.interactions;

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
    // in onCreate() set `this.isEditing` to `true` once the user starts editing, set to `false` once done editing
    // in onBackPressed() check `if(this.isEditing)` to understand what to do

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_title);

        // find all views
        FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
        FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
        TextView textViewTitle = findViewById(R.id.textViewPageTitle);
        EditText editTextTitle = findViewById(R.id.editTextPageTitle);

        // setup - start from static title with "edit" button
        fabStartEdit.setVisibility(View.VISIBLE);
        fabEditDone.setVisibility(View.GONE);
        textViewTitle.setText("Page title here");
        textViewTitle.setVisibility(View.VISIBLE);
        editTextTitle.setText("Page title here");
        editTextTitle.setVisibility(View.GONE);

        // handle clicks on "start edit"
        fabStartEdit.setOnClickListener(v -> {
            this.isEditing = true;
            // animate out the "start edit" FAB and animate in the "done edit" FAB
            this.fadeOutAnimation(fabStartEdit);
            this.fadeInAnimation(fabEditDone);
            // hide the static title and show the editable title
            textViewTitle.setVisibility(View.GONE);
            editTextTitle.setVisibility(View.VISIBLE);
            // make sure the editable title's text is the same as the static one
            editTextTitle.setText(textViewTitle.getText());
            // make the keyboard to open with the edit-text focused
            this.showSoftKeyboard(editTextTitle);
        });

        // handle clicks on "done edit"
        fabEditDone.setOnClickListener(v -> {
            this.isEditing = false;
            // animate out the "done edit" FAB and animate in the "start edit" FAB
            this.fadeOutAnimation(fabEditDone);
            this.fadeInAnimation(fabStartEdit);
            // set the user's input txt from the edit-text as the static text-view text
            textViewTitle.setText(editTextTitle.getText());
            // show the static title (text-view) and hide the editable title (edit-text)
            textViewTitle.setVisibility(View.VISIBLE);
            editTextTitle.setVisibility(View.GONE);
            // close the keyboard
            this.hideSoftKeyboard(editTextTitle);
        });
    }

    @Override
    public void onBackPressed()
    {
        // BACK button was clicked
        FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
        FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
        TextView textViewTitle = findViewById(R.id.textViewPageTitle);
        EditText editTextTitle = findViewById(R.id.editTextPageTitle);

        if (this.isEditing)
        {
            this.isEditing = false;
            editTextTitle.setVisibility(View.GONE); // hide the edit-text
            textViewTitle.setVisibility(View.VISIBLE); // show the static text-view with previous text
            // animate out the "done-edit" FAB and animate in the "start-edit" FAB
            this.fadeOutAnimation(fabEditDone);
            this.fadeInAnimation(fabStartEdit);
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
                .withEndAction(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        view.setVisibility(View.INVISIBLE);
                    }
                })
                .start();
    }

}