package com.smartlab.testdynamicswitches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private HashMap<Enum<?>, Switch> switches = new HashMap<>();
    private HashMap<View, String> buttons = new HashMap<>();
    private HashMap<String, EditText> inputFields = new HashMap<>();

    int agentTagValue = 0;
    int taxCodeValue = 0;
    int operatingModeValue = 0;

    LinearLayout llMain;

    private void registration() {
        taxCodeValue = getSwitchesValue(FiscalCoreEnums.TaxCode.values());
        operatingModeValue = getSwitchesValue(FiscalCoreEnums.OperatingMode.values());
        agentTagValue = getSwitchesValue(FiscalCoreEnums.AgentTag.values());

        String cashierCodeName = inputFields.get("etCashier").getText().toString();
        String regNumber = inputFields.get("etRegNum").getText().toString();
        String INN = inputFields.get("etINN").getText().toString();

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

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> int getSwitchesValue(T[] aValues) {
        int newValue = 0;
        for (T element: aValues) {
            Switch sw = switches.get(element);
            if (sw != null && sw.isChecked()) {
                newValue += element.getValue();
            }
        }
        return newValue;
    }

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> void drawSwitches(T[] aValues, int value) {
        drawSwitches(aValues, value, llMain);
    }

    private <T extends Enum<T> & FiscalCoreEnums.EnumWithValueAndDescription> void drawSwitches(T[] aValues, int value, ViewGroup parent) {
        for (T element: aValues) {
            if (element.getValue() == 0) {
                continue;
            }
            Switch sw = switches.get(element);
            if (sw == null) {
                sw = new Switch(this);
                sw.setText(element.getDescription());

                switches.put(element, sw);

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
        tv.setTextSize(16);
        tv.setText(title);
        parent.addView(tv);
    }

    public void drawEditText(String name, int inputType) {
        drawEditText(name, inputType, llMain);
    }
    public void drawEditText(String name, int inputType, String defaultString) {
        drawEditText(name, inputType, defaultString, llMain);
    }

    public void drawEditText(String name, int inputType, ViewGroup parent) {
        drawEditText(name, inputType, "", llMain);
    }

    public void drawEditText(String name, int inputType, String defaultString, ViewGroup parent) {
        EditText et = new EditText(this);
//        int stringByName = getResources().getIdentifier(name, "string", this.getPackageName());
//        et.setHint(getResources().getString(stringByName));
        et.setTextSize(16);
        if (!defaultString.isEmpty()) {
            et.setText(defaultString);
        }
        et.setInputType(inputType);

        inputFields.put(name, et);

        parent.addView(et);
    }

    public void drawDivider() {
        drawDivider(llMain);
    }

    public void drawDivider(ViewGroup parent) {
        ContextThemeWrapper themeContext = new ContextThemeWrapper(this, R.style.Divider);
        View divider = new View(themeContext, null, 0);

        parent.addView(divider);
    }

    public void drawButton(String name) {
        drawButton(name, llMain);
    }

    public void drawButton(String name, ViewGroup parent) {
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        int stringByName = getResources().getIdentifier(name, "string", this.getPackageName());
        btn.setText(getResources().getString(stringByName));

        buttons.put(btn, name);

        parent.addView(btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = findViewById(R.id.llMain);

        drawTextView(getResources().getString(R.string.btnRegistration));

        drawButton("btnRefresh");

        drawTextView(getResources().getString(R.string.etINN));
        drawEditText("etINN", InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //drawDivider();

        drawTextView(getResources().getString(R.string.etRegNum));
        drawEditText("etRegNum", InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //drawDivider();

        drawTextView(getResources().getString(R.string.titleTaxCode));
        drawSwitches(FiscalCoreEnums.TaxCode.values(), taxCodeValue);

        drawDivider();

        drawTextView(getResources().getString(R.string.titleOperatingMode));
        drawSwitches(FiscalCoreEnums.OperatingMode.values(), operatingModeValue);

        drawDivider();

        drawTextView(getResources().getString(R.string.titleAgentTag));
        drawSwitches(FiscalCoreEnums.AgentTag.values(), agentTagValue);

        drawDivider();

        drawTextView(getResources().getString(R.string.etCashier));
        drawEditText("etCashier", InputType.TYPE_NUMBER_FLAG_DECIMAL, "кассир1");

        drawButton("btnRegistration");
        drawButton("btnReRegistration");
        drawButton("btnArchiveClosure");
    }

    @Override
    public void onClick(View view) {
        String buttonName = buttons.get(view);
        if (buttonName == null) return;
        switch (buttonName) {
            case "btnRefresh":
                showSwitches(FiscalCoreEnums.AgentTag.values());
                break;
            case "btnRegistration":
                registration();
                break;
            case "btnReRegistration":
                showToast(getResources().getString(R.string.btnReRegistration));
                registration();
                break;
            case "btnArchiveClosure":
                showToast(getResources().getString(R.string.btnArchiveClosure));
                break;
        }
    }
}
