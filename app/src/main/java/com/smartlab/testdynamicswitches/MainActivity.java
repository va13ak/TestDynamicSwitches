package com.smartlab.testdynamicswitches;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    final static int AGENT_TAGS_STARTING_INDEX = 1000;
    final static int TAX_CODE_STARTING_INDEX = 2000;
    final static int OPERATING_MODE_STARTING_INDEX = 3000;

    private static DisplayMetrics metrics;

    int agentTagValue = 0;
    int taxCodeValue = 0;
    int operatingModeValue = 0;

    Button btnRefresh;
    Button btnRegistration;
    Button btnReRegistration;
    Button btnArchiveClosure;
    LinearLayout llMain;
    View firstDivider;

    private void registration() {
        taxCodeValue = getSwitchesValue(FiscalCoreEnums.AgentTag.values(), TAX_CODE_STARTING_INDEX);
        operatingModeValue = getSwitchesValue(FiscalCoreEnums.AgentTag.values(), OPERATING_MODE_STARTING_INDEX);
        agentTagValue = getSwitchesValue(FiscalCoreEnums.AgentTag.values(), AGENT_TAGS_STARTING_INDEX);
        String cashierCodeName = "Кассир 1";
        String regNumber = ((TextView)findViewById(R.id.etRegNumber)).getText().toString();
        String INN = ((TextView)findViewById(R.id.etINN)).getText().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.btnRegistration)).append("\n");
        sb.append("TaxCode: 0x").append(Integer.toHexString(taxCodeValue)).append(" (").append(Integer.toBinaryString(taxCodeValue)).append(")").append("\n");
        sb.append("OperatingMode: 0x").append(Integer.toHexString(operatingModeValue)).append(" (").append(Integer.toBinaryString(operatingModeValue)).append(")").append("\n");
        sb.append("AgentTag: 0x").append(Integer.toHexString(agentTagValue)).append(" (").append(Integer.toBinaryString(agentTagValue)).append(")").append("\n");
        sb.append("cashierName: ").append(cashierCodeName).append("\n");
        sb.append("regNumber: ").append(regNumber).append("\n");
        sb.append("INN: ").append(INN);
        showToast(sb.toString());
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> void showSwitches(T[] aValues) {
        StringBuilder sbMessage = new StringBuilder();
        for (T element: aValues) {
            StringBuilder sb = new StringBuilder();
            sb.append("value: ");
            sb.append(element.getValue());
            sb.append(" - ");
            sb.append(element);
            sb.append(": ");
            sb.append(element.getDescription());

            sbMessage.append(sb);
            sbMessage.append('\n');
        }
        showToast(sbMessage.toString());
    }

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> int getSwitchesValue(T[] aValues, int startingIndex) {
        int newValue = 0;
        for (T element: aValues) {
            Switch sw = findViewById(startingIndex + element.getValue());
            if (sw != null && sw.isChecked()) {
                newValue += element.getValue();
            }
        }
        return newValue;
    }

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> void drawSwitches(T[] aValues, int startingIndex, int value) {
        drawSwitches(aValues, startingIndex, value, llMain);
    }

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> void drawSwitches(T[] aValues, int startingIndex, int value, ViewGroup parent) {
        for (T element: aValues) {
            if (element.getValue() == 0) {
                continue;
            }
            int switchId = startingIndex + element.getValue();
            Switch sw = findViewById(switchId);
            if (sw == null) {
                sw = new Switch(this);
                sw.setText(element.getDescription());
                sw.setOnClickListener(this);
                sw.setId(switchId);

                parent.addView(sw);
            }
            sw.setChecked((value & element.getValue()) == element.getValue());
        }
    }

    public void drawTextView(String title) {
        drawTextView(title, llMain);
    }

    public void drawTextView(String title, ViewGroup parent) {
        TextView tv = new TextView(this);
        tv.setText(title);
        parent.addView(tv);
    }

    /**
     * Converts a dp value to pixels.
     * @param dp
     * @return Pixel value of dp.
     */
    public static float dpToPix(float dp) {
        //return SCALE * dp + FLOAT_INT_AVG_NUDGE;
        //InternalDimension id = new InternalDimension(dp, TypedValue.COMPLEX_UNIT_DIP);
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public void drawDivider() {
        drawDivider(llMain);
    }

    public void drawDivider(ViewGroup parent) {
        ContextThemeWrapper themeContext = new ContextThemeWrapper(this, R.style.Divider);
        View divider = new View(themeContext, null, 0);

        //View divider = new View(this);
        //divider.setLayoutParams(firstDivider.getLayoutParams());

//        View divider = new View(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.FILL_PARENT, (int)dpToPix(1));
//        //divider.setLayoutParams(firstDivider.getLayoutParams());
//        //TypedArray array = getApplicationContext().getTheme()
//        TypedArray array = getTheme().obtainStyledAttributes(new int[] {android.R.attr.listDivider});
//        Drawable draw = array.getDrawable(0);
//        array.recycle();
//        divider.setBackgroundDrawable(draw);

        parent.addView(divider);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        metrics = getResources().getDisplayMetrics();

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);

        btnRegistration = findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(this);

        btnReRegistration = findViewById(R.id.btnReRegistration);
        btnReRegistration.setOnClickListener(this);

        btnArchiveClosure = findViewById(R.id.btnArchiveClosure);
        btnArchiveClosure.setOnClickListener(this);

        firstDivider = findViewById(R.id.divider);

        llMain = findViewById(R.id.llMain);


        drawDivider();
        drawTextView(getResources().getString(R.string.titleTaxCode));
        drawSwitches(FiscalCoreEnums.TaxCode.values(), TAX_CODE_STARTING_INDEX, taxCodeValue);

        drawDivider();
        drawTextView(getResources().getString(R.string.titleOperatingMode));
        drawSwitches(FiscalCoreEnums.OperatingMode.values(), OPERATING_MODE_STARTING_INDEX, operatingModeValue);

        drawDivider();
        drawTextView(getResources().getString(R.string.titleAgentTag));
        drawSwitches(FiscalCoreEnums.AgentTag.values(), AGENT_TAGS_STARTING_INDEX, agentTagValue);
    }

    @Override
    public void onClick(View view) {
        int elementId = view.getId();
        switch (elementId) {
            case R.id.btnRefresh:
                showSwitches(FiscalCoreEnums.AgentTag.values());
                break;
            case R.id.btnRegistration:
                //showToast(getResources().getString(R.string.btnRegistration));
                registration();
                break;
            case R.id.btnReRegistration:
                showToast(getResources().getString(R.string.btnReRegistration));
                registration();
                break;
            case R.id.btnArchiveClosure:
                showToast(getResources().getString(R.string.btnArchiveClosure));
                break;
            default:
                if ((elementId % AGENT_TAGS_STARTING_INDEX) == (elementId - AGENT_TAGS_STARTING_INDEX)) {
                    showToast(FiscalCoreEnums.getElementsDescriptionByValue(FiscalCoreEnums.AgentTag.values(), elementId % AGENT_TAGS_STARTING_INDEX));
                } else if ((elementId % TAX_CODE_STARTING_INDEX) == (elementId - TAX_CODE_STARTING_INDEX)) {
                    showToast(FiscalCoreEnums.getElementsDescriptionByValue(FiscalCoreEnums.TaxCode.values(), elementId % TAX_CODE_STARTING_INDEX));
                } else if ((elementId % OPERATING_MODE_STARTING_INDEX) == (elementId - OPERATING_MODE_STARTING_INDEX)) {
                    showToast(FiscalCoreEnums.getElementsDescriptionByValue(FiscalCoreEnums.OperatingMode.values(), elementId % OPERATING_MODE_STARTING_INDEX));
                }
        }
    }
}
