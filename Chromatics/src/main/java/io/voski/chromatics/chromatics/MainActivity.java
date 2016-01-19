package io.voski.chromatics.chromatics;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
  Button mTestBtn;
  EditText mEnabledInput;
  EditText mDisabledInput;
  EditText mPressedInput;
  EditText mEnabledTextInput;
  EditText mDisabledTextInput;
  EditText mPressedTextInput;
  CheckBox mCheckbox;
  TextView mDensityText;
  TextView mHeightText;
  TextView mWidthText;

  private Map<Integer, String> densityMap = new HashMap<Integer, String>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    buildDensityMap();

    mTestBtn = (Button) findViewById(R.id.btn_1);
    mEnabledInput = (EditText) findViewById(R.id.btn_1_enabled);
    mPressedInput = (EditText) findViewById(R.id.btn_1_pressed);
    mDisabledInput = (EditText) findViewById(R.id.btn_1_disabled);
    mEnabledTextInput = (EditText) findViewById(R.id.btn_1_enabled_text);
    mPressedTextInput = (EditText) findViewById(R.id.btn_1_pressed_text);
    mDisabledTextInput = (EditText) findViewById(R.id.btn_1_disabled_text);
    mCheckbox = (CheckBox) findViewById(R.id.btn_1_state);
    mDensityText = (TextView) findViewById(R.id.density_text_view);
    mHeightText = (TextView) findViewById(R.id.height_text_view);
    mWidthText = (TextView) findViewById(R.id.width_text_view);

    mCheckbox.setChecked(true);
    mCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mTestBtn.setEnabled(isChecked);
      }
    });

    mDensityText.setText(getDensityString());
    mHeightText.setText(String.valueOf(getHeight()));
    mWidthText.setText(String.valueOf(getWidth()));
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


    StateListDrawable backgroundDrawable = createDrawable(enabledColor, pressedColor, disabledColor);

    mTestBtn.setBackground(backgroundDrawable);
  }

  public int getDensity() {
    return getResources().getDisplayMetrics().densityDpi;
  }

  public String getDensityString() {
    int density = getDensity();
    String result = "UNKOWN";

    if (densityMap.containsKey(density)) {
      result = densityMap.get(density);
    }

    return result;
  }

  private void buildDensityMap() {
    this.densityMap.put(DisplayMetrics.DENSITY_LOW, "LDPI");
    this.densityMap.put(DisplayMetrics.DENSITY_MEDIUM, "MDPI");
    this.densityMap.put(DisplayMetrics.DENSITY_HIGH, "HDPI");
    this.densityMap.put(DisplayMetrics.DENSITY_XHIGH, "XHDPI");
    this.densityMap.put(DisplayMetrics.DENSITY_TV, "TVDPI");
  }

  private StateListDrawable createDrawable(int enabled, int pressed, int disabled) {
    StateListDrawable stateListDrawable = new StateListDrawable();
    stateListDrawable.addState(new int[] { -android.R.attr.state_pressed, android.R.attr.state_enabled }, new ColorDrawable(enabled));
    stateListDrawable.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, new ColorDrawable(pressed));
    stateListDrawable.addState(new int[] { -android.R.attr.state_enabled }, new ColorDrawable(disabled));

    return stateListDrawable;
  }

  private int getWidth() {
   return getWindowManager().getDefaultDisplay().getWidth();
  }

  private int getHeight() {
    return getWindowManager().getDefaultDisplay().getHeight();
  }
}
