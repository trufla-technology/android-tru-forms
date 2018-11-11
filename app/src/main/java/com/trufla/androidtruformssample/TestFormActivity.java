package com.trufla.androidtruformssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.SchemaViews;
import com.trufla.androidtruforms.TruFormFragment;
import com.trufla.androidtruforms.TruNavigationActivity;
import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;

import java.io.InputStream;
import java.util.Scanner;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class TestFormActivity extends AppCompatActivity implements TruFormFragment.OnFormActionsListener {

    public static final String JSON_STR = "JSON_STR";

    enum FormType {FRAGMENT_FORM, ACTIVITY_FORM}

    FormType type = FormType.FRAGMENT_FORM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (type == FormType.FRAGMENT_FORM) {
            setContentView(R.layout.test_form_fragment_activity);
            fragmentFormParseClicked();
        } else
            setContentView(R.layout.test_form_activity);

    }

    public void onParseClick(View view) {
        activityFormParseClicked();

    }

    private void fragmentFormParseClicked() {
        StringBuilder jsonStringBuilder = new StringBuilder();
        String js = "";
        String v = "{\"vehicle\":2247,\"driver_information\":{\"description\":\"desc\",\"date_time_of_accident\":\"2018-11-11 11:51:13\",\"date_of_accident\":\"2018-11-11\",\"location_of_accident\":\"1231232,1231232\",\"where_you_driving\":\"No\",\"number_of_people\":3,\"who_was_driving\":\"I was\",\"damage_photos\":[{\"photo\":\"UklGRogFAABXRUJQVlA4IHwFAACwHwCdASpwAMgAPm00l0ikIqIhIjK6EIANiWVu3/AZ3nQ+Dn9kv5AX1duOwz9gG2k/lXoA/Tv1hfQBvAH6V+wB5cHsd/3jBK/5n22/56vH37/dVkj3XfIvBr+qcRP1V56f8p4JnjnqRf3P9VfWv/5ftJ9on0N7Av6u9XT9h/Y4/VAAbbv1Nxv9GQuczPtpzq1MYLDO2EMbs93TrpJAEyR/IYLoKiV4ftaGgoFsX0+YPf1RicYS+oBbUmGrLuyXWRbSmtJH3uOkFlLE5w6JKCgWxfVxRos+p4JxfrvuUP9WTnUWknnWdXg4HARE5WKwxtWzzC44EEkRyCJD9Z5rpJ8EbzAI73QAAN43cMjizGiYZK9i8nnfZzxmGj5TjLP+BmAje5pRzGHlph002NW/Teo3P/KivX+t9bPZ7IP1jFI+nZappMh8uoxk+7fG8f6X7j//r7jhsue77HR5VOtfZpec4QX5ouerZNVP/y33igauNu6EtA6oXURFo+Cc67I1Os20Rsdt4DnDDE+uXtlgp6HIeEUMhhNpYkRHeDx6L0eRjHAuizAbKwTfXygN2jXf1A4ifBwuY9hlhXUNm7XTg2hoqyriB43Go2sEfdcCAv0F7ofJxOKgsTFsjn0FdmBrNYGd+EtGHLbnlLZYK3wP7kvKzohpI3RJ1Kqs649EGEu1LHuf+eO0nAXxYwKyZCVF9VREOsBY5SlW1/lIzjS9ewl5FUEzpmXSnlq4AiyuTxgfgZ21SwbnKMGvPrj5imZu3pnMUvGh0xPR77d40NviGzfzgKxHD9kt/YXvbsmGKSOiaG1DPgJnBvcCwXza1h7dkFYsmIkTE8e3nIrDyd+VIIzrB/RWM2Qis9uYtl4qSAjfwRiTUh1999mvNfM06FuKbmSlUswLjfPniJjZS2gNV7P+VR/k3/XWtHlUf5GdkRxtAn3odV7QJaDpveyEMFz8EvuEBNt7o4lEOYi8B3t+VuXKplOpd7ibS9n90vGRqoqFIsWhp11mKMujTJWlRCvWcD7VLi+yewtQYnw1CfylZNKSxDNFw/hjj4szlaUADZiNMDvSmfnHDJt7IeaUel7t2s/br9VnhvmzgM4O9amrngSKfXlReX4WiERoxTHMYM9owkN3/UNpex9m/dto/sxKtPSC8hnwWwvZMuOsl8fGIyWeThwH1YS1JjwLbtE71Fr1NoACZgFr6MY74gAGoFb27chit6suJ/706qYnly2ieZjf9TsIbzpMGEKeyeH3RiXOACx1lPgTlAIAw5w6FyFLU3eoqVICAnR0CNQsBBYns8tVFcVjs288cEOzIM0+9XxTUu7mlyMt156+WR4HohR30zf39QgR2dinLPSHU+YAAALE5ls+Tp3AaW5fY3seLRgsm/iqS9nrHXI1Qttd3GPzRIX5hMK1GoXeaUiH//I0/oDCuKYh806w07DRzNMs91lDiGWLZVkj6IdKFkgy3PXPbV/4so3Ou6fZhg+v1Auovib4a1S+gtjr0laT26Kyu57kEmeO6TvEu7qbJPmjkLj0EM5/V8zlG/1XcNrwjthJuPPwENJn35QnnrwIKiW43e4x9QtDvbytDI0o34Ikw59nRiTPrfIun6KUrmX4Fk4+tDVvkvthFM57kweq747tj2395knodBlcqPqUWSdqdvZ0l/raWMfYGDcR3FWSl9ArW+H7yDPWa6Zo0kBOSUFq26ABp6OopIapXnQWd9zAjMYabFd5oitR+X7HcaRG24CMzB6tAfYwXxxk+Bm+H6HzzTWZxMTDOgtEqsP0XUDjiE2z7DrKr6LQVy9isf8Vq0A01svF+pDk0A98rgGMJuIR5uVQESF1jcD2MnVmOv8NwwGnxHpRUl1X+TrwdB6AAAA=\"},{\"photo\":\"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\"},{\"photo\":\"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\"}]},\"other_driver_information\":{\"information\":[{\"document_photos\":{\"pink_card\":null,\"drivers_license\":null,\"license_plate\":\"UklGRnwFAABXRUJQVlA4IHAFAAAQJwCdASpwAMgAPm0uk0WkIqGYnLzEQAbEsgHYGFzs0gmfu343d4ZkTuH5Efzbp0ujvCHMxoW6/v3f3R/AP+8/zz3p/cd7T/Q8/pHoA/i/9G/Yz3lf7l6rPQA/p/nc+zj6C3lmftN8Rf7qekBmJfACujv0mcQqUvlkJRsYW4LB0hqFT8jU4/s+K5XHFfNVR7UyoPXsj6GcJlBytFU8xEznqZgPNAkk1lDce3dweb29vEu0KU1w1cSp6Vk8aUZ3rOT5tBDh8qsA5jjtSsV+UTn1Rrmtc0lweI5+dIs04Pj/YkRaxrpU972tOhDVHlBCCpZKwPeKW+4jbhZPIybDLY++0vMxcKDr9C/xR/PGi37QrNrNYOX6vHEP4J1HV5JtRiHmCxroO8C9x9HTg/3XFxddSIKjmngiVeh4wkrq9eRdt1gA/ue74iFV/MNIFvTq3l+sGQBkDeMY+9XouuHeJ116WdrUZzHk/A4j3J3VDgNKLYRaj8/gR3cbyvdsWLIbX7VbbGyfvuaVTXvX/YHNyEFWupQlnWhnACVIfOsfxSlPgEJrRRz+dslQ6MybswL0pWIahNre/RZYiFmUwTM9pvexscNQ4eHxMOVLwAAD9rX2mqaZSz6mLMmE+jWwtZofXMnZoYsMT2An8CNuQc73OyVfIs+yMz9yyIo5ZswAOqHoN1V5veto4ZQcTj7reHL0g9w17KcbZKMdnyJWnh4VjEVg8B1UxX23uOdJY+gNC4zg6G0hKJ1q7ddbEM44wo+4SiCAjqAzijEDLIAkhCdG3f70QFTBdxeAahq94rAP0DBerf5/AFrvw+j+TsMslKWC2SzMUjQvfTp08NZSASi/g0tyRehUJ5/6IuKYZABNICaWSA69fucaLSuVz78wYwHG9w9lQB7mct+pC9rogbJIxsOGmmpnjZ5E3yYs7WLkx09loTKYFMZ5l7+uK+pc6ehHLgl099gWvz2eELkhW4IBJxVdQko0LK9Ddnb14+G+/+/9QzaS71JljCB9nHCrAwHUTVEIyoVKDqkCxQBWDl33FWawl3epTHtf3HTct9BoLCcNrdwJXNM0vG46K6s/31rU6JNG5XbuvEhwOBhzdbWl+v8Pza8xO2MTLQk70LbikbvN/CPQ/c0mf1mJy/fxx4ET7zGIEuK6IZ2DlhrZ4CA3JLpGm9RnRnwC9EpY/chNleZeBotR2oaFYtan3UOXbtV4UtVt1u0S/w2hMhRbFhbDdGYpoCqwjn3muGzoQpzSAA672wLF9hSSTMplLTSR9L65hZTsaprsp4R2sXKJDaKsXTfbWLpvxB+ZhNCZlWe6ADhuLPcsP9M8Ga0d0paJLA+81+5Elsiesv8/OZQEhGEL7QUYpG8uKxZj/YxxbfRXbQ4dmzIse0LUaNMj3rVrXtTiW1vBQbw3r4J/6+wD6gDQpSPiSHAjmFbYI6H9WISbgVqHGgeNswcWql/jP0eIv7zH/wLqcZu2WS7sQR9zIUKhzODbFuAAg6zbzKNxscBtoDKiyeVl0u1iMBC53pvCAt6jOjNMFkz9Pn6R3IrhlCfwUFP+TWvwUE8yhv9kFVQ39C3rtow+Ewtfjju5wzpA1wN9k3flBRRDnXJMp613DE1NYLhw7kUzDHeknxVEtxgZ8C7E42g1b6nTOcnEthAuX3seAoP/ctKtzlugBkXCDEC7f8p202VkHwZS2JAqUy0A9hBVbFchfdP3Vp4Qkiu9aIrOjwD5RJlgYMi07wRImgMMcPxPY2pKq+FLgy4DUnIrtyh5mm4g8CRKp5xqy/vFcFdSV7GRq+kR7knJMGxvWKC/EYwm8UP+leqmM/8hMpDuH0LNdEUb2ql1y7cWkBBEYAAAAAA=\",\"registration\":\"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\"},\"document_details\":{\"first_name\":\"11111\",\"last_name\":\"11111\",\"address\":\"111111\",\"city\":\"1111\",\"province\":\"111\",\"postal_code\":\"\",\"home_phone_number\":\"1111\",\"cell_phone_number\":\"\",\"email\":\"\",\"drivers_license_number\":\"\",\"license_plate\":\"\",\"car\":{\"year\":\"1111\",\"make\":\"1111\",\"model\":\"1111\"},\"policy_number\":\"1111\",\"insurance_company\":\"1111\"}},{\"document_photos\":{\"pink_card\":\"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\",\"drivers_license\":\"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\",\"license_plate\":null,\"registration\":null},\"document_details\":{\"first_name\":\"22222\",\"last_name\":\"22222\",\"address\":\"222\",\"city\":\"2\",\"province\":\"2\",\"postal_code\":\"2\",\"home_phone_number\":\"2\",\"cell_phone_number\":\"2\",\"email\":\"2\",\"drivers_license_number\":\"2\",\"license_plate\":\"2\",\"car\":{\"year\":\"222\",\"make\":\"222\",\"model\":\"222\"},\"policy_number\":\"2\",\"insurance_company\":\"2\"}}]},\"witness_information\":{\"witnesses\":[{\"name\":\"a\",\"email\":\"a\",\"home_phone_number\":\"a\",\"cell_phone_number\":\"a\"},{\"name\":\"b\",\"email\":\"b\",\"home_phone_number\":\"b\",\"cell_phone_number\":\"b\"}]}}";
        if (TextUtils.isEmpty(js)) {
            InputStream inputStream = getResources().openRawResource(R.raw.claims_edited);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
        } else if (TextUtils.isEmpty(v))
            jsonStringBuilder.append(js);

        SchemaBuilder schemaBuilder = SchemaBuilder.getInstance().allowDefaultOrder(true);
        schemaBuilder.getRequestBuilder().url("http://www.mocky.io/v2");
        try {
            if (TextUtils.isEmpty(v))
                SchemaViews.showFragment(jsonStringBuilder.toString(), this, getSupportFragmentManager(), R.id.container);
            else
                SchemaViews.showFragmentWithConstValue(jsonStringBuilder.toString(), v, this, getSupportFragmentManager(), R.id.container);
        } catch (UnableToParseSchemaException e) {
            e.printStackTrace();
        }

    }

    private void activityFormParseClicked() {
        StringBuilder jsonStringBuilder = new StringBuilder();
        String js = ((EditText) findViewById(R.id.et)).getText().toString().trim();
        String v = ((EditText) findViewById(R.id.submitted_data)).getText().toString().trim();

        if (TextUtils.isEmpty(js)) {
            Toast.makeText(this, "No Json Entered ... Form from claims.json will be built", Toast.LENGTH_LONG).show();
            InputStream inputStream = getResources().openRawResource(R.raw.claims_edited);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                jsonStringBuilder.append(scanner.nextLine());
            }
        } else if (TextUtils.isEmpty(v))
            jsonStringBuilder.append(js);

        SchemaBuilder schemaBuilder = SchemaBuilder.getInstance().allowDefaultOrder(true);
        schemaBuilder.getRequestBuilder().url("http://www.mocky.io/v2");
        if (TextUtils.isEmpty(v))
            SchemaViews.startActivityForResult(this, jsonStringBuilder.toString());
        else
            SchemaViews.startActivityToRenderConstSchema(this, js, v);

        //startActivity(new Intent(this, TruNavigationActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SchemaBuilder.REQUEST_CODE && resultCode == RESULT_OK) {
            String str = data.getStringExtra(SchemaBuilder.RESULT_DATA_KEY);
            ((TextView) findViewById(R.id.submitted_data)).setText(str);
            Log.d("Json values", str);
        }
    }


    @Override
    public void onFormSubmitted(String jsonReperesentation) {
        String str = jsonReperesentation;
        //((TextView) findViewById(R.id.submitted_data)).setText(str);
        Log.d("Json values", str);
    }

    @Override
    public void onFormFailed() {
        Toast.makeText(this, "Unable to create the form ... please check the schema", Toast.LENGTH_SHORT).show();
    }

}
