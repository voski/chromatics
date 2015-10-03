package io.voski.chromatics.chromatics;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.StateSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {
  Button mTestBtn;
  EditText mEnabledInput;
  EditText mDisabledInput;
  EditText mPressedInput;
  CheckBox mCheckbox;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTestBtn = (Button) findViewById(R.id.btn_1);
    mEnabledInput = (EditText) findViewById(R.id.btn_1_enabled);
    mPressedInput = (EditText) findViewById(R.id.btn_1_disabled);
    mDisabledInput = (EditText) findViewById(R.id.btn_1_pressed);
    mCheckbox = (CheckBox) findViewById(R.id.btn_1_state);

    mCheckbox.setChecked(true);
    mCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mTestBtn.setEnabled(isChecked);
      }
    });
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatementx
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void saveColors(View view) {
    String enabled = mEnabledInput.getText().toString();
    String pressed = mPressedInput.getText().toString();
    String disabled = mDisabledInput.getText().toString();
    int enabledColor;
    int pressedColor;
    int disabledColor;
    StateListDrawable state = new StateListDrawable();

    // empty imputs
    if (enabled.isEmpty() || disabled.isEmpty() || pressed.isEmpty()) {
      Toast.makeText(this, getString(R.string.blank_input_message),Toast.LENGTH_SHORT).show();
      return;
    }

    try {
      enabledColor = Color.parseColor(enabled);
      disabledColor = Color.parseColor(disabled);
      pressedColor = Color.parseColor(pressed);
    } catch (IllegalArgumentException e) { // can't parse color
      Toast.makeText(this, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
      return;
    } catch (Exception e) { // catch something I might have missed
      e.printStackTrace();
      Toast.makeText(this, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
      return;
    }

    state.addState(new int[] { -android.R.attr.state_pressed, android.R.attr.state_enabled }, new ColorDrawable(enabledColor));
    state.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, new ColorDrawable(pressedColor));
    state.addState(new int[] { -android.R.attr.state_enabled }, new ColorDrawable(disabledColor));

    mTestBtn.setBackground(state);
  }
}
