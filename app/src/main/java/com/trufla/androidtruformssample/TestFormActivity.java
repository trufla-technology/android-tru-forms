package com.trufla.androidtruformssample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trufla.androidtruforms.SchemaBuilder;
import com.trufla.androidtruforms.SchemaViews;
import com.trufla.androidtruforms.TruFormFragment;
import com.trufla.androidtruforms.exceptions.UnableToParseSchemaException;
import java.io.InputStream;
import java.util.Scanner;


public class TestFormActivity extends AppCompatActivity implements TruFormFragment.OnFormActionsListener {

    public static final String JSON_STR = "JSON_STR";
    private static int schemaType = 1;

    enum FormType {FRAGMENT_FORM, ACTIVITY_FORM}

    FormType type = FormType.ACTIVITY_FORM;

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
        String v ="";//"{\"vehicle\":\"null\",\"driver_information\":{\"description\":\"sssss\",\"date_time_of_accident\":\"2018-11-04 08:20:17\",\"date_of_accident\":\"2018-11-22\",\"location_of_accident\":\"21221,2122\",\"where_you_driving\":\"No\",\"number_of_people\":4,\"who_was_driving\":\"me\",\"damage_photos\":[{\"photo\":\"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\"},{\"photo\":\"UklGRogFAABXRUJQVlA4IHwFAACwHwCdASpwAMgAPm00l0ikIqIhIjK6EIANiWVu3/AZ3nQ+Dn9kv5AX1duOwz9gG2k/lXoA/Tv1hfQBvAH6V+wB5cHsd/3jBK/5n22/56vH37/dVkj3XfIvBr+qcRP1V56f8p4JnjnqRf3P9VfWv/5ftJ9on0N7Av6u9XT9h/Y4/VAAbbv1Nxv9GQuczPtpzq1MYLDO2EMbs93TrpJAEyR/IYLoKiV4ftaGgoFsX0+YPf1RicYS+oBbUmGrLuyXWRbSmtJH3uOkFlLE5w6JKCgWxfVxRos+p4JxfrvuUP9WTnUWknnWdXg4HARE5WKwxtWzzC44EEkRyCJD9Z5rpJ8EbzAI73QAAN43cMjizGiYZK9i8nnfZzxmGj5TjLP+BmAje5pRzGHlph002NW/Teo3P/KivX+t9bPZ7IP1jFI+nZappMh8uoxk+7fG8f6X7j//r7jhsue77HR5VOtfZpec4QX5ouerZNVP/y33igauNu6EtA6oXURFo+Cc67I1Os20Rsdt4DnDDE+uXtlgp6HIeEUMhhNpYkRHeDx6L0eRjHAuizAbKwTfXygN2jXf1A4ifBwuY9hlhXUNm7XTg2hoqyriB43Go2sEfdcCAv0F7ofJxOKgsTFsjn0FdmBrNYGd+EtGHLbnlLZYK3wP7kvKzohpI3RJ1Kqs649EGEu1LHuf+eO0nAXxYwKyZCVF9VREOsBY5SlW1/lIzjS9ewl5FUEzpmXSnlq4AiyuTxgfgZ21SwbnKMGvPrj5imZu3pnMUvGh0xPR77d40NviGzfzgKxHD9kt/YXvbsmGKSOiaG1DPgJnBvcCwXza1h7dkFYsmIkTE8e3nIrDyd+VIIzrB/RWM2Qis9uYtl4qSAjfwRiTUh1999mvNfM06FuKbmSlUswLjfPniJjZS2gNV7P+VR/k3/XWtHlUf5GdkRxtAn3odV7QJaDpveyEMFz8EvuEBNt7o4lEOYi8B3t+VuXKplOpd7ibS9n90vGRqoqFIsWhp11mKMujTJWlRCvWcD7VLi+yewtQYnw1CfylZNKSxDNFw/hjj4szlaUADZiNMDvSmfnHDJt7IeaUel7t2s/br9VnhvmzgM4O9amrngSKfXlReX4WiERoxTHMYM9owkN3/UNpex9m/dto/sxKtPSC8hnwWwvZMuOsl8fGIyWeThwH1YS1JjwLbtE71Fr1NoACZgFr6MY74gAGoFb27chit6suJ/706qYnly2ieZjf9TsIbzpMGEKeyeH3RiXOACx1lPgTlAIAw5w6FyFLU3eoqVICAnR0CNQsBBYns8tVFcVjs288cEOzIM0+9XxTUu7mlyMt156+WR4HohR30zf39QgR2dinLPSHU+YAAALE5ls+Tp3AaW5fY3seLRgsm/iqS9nrHXI1Qttd3GPzRIX5hMK1GoXeaUiH//I0/oDCuKYh806w07DRzNMs91lDiGWLZVkj6IdKFkgy3PXPbV/4so3Ou6fZhg+v1Auovib4a1S+gtjr0laT26Kyu57kEmeO6TvEu7qbJPmjkLj0EM5/V8zlG/1XcNrwjthJuPPwENJn35QnnrwIKiW43e4x9QtDvbytDI0o34Ikw59nRiTPrfIun6KUrmX4Fk4+tDVvkvthFM57kweq747tj2395knodBlcqPqUWSdqdvZ0l/raWMfYGDcR3FWSl9ArW+H7yDPWa6Zo0kBOSUFq26ABp6OopIapXnQWd9zAjMYabFd5oitR+X7HcaRG24CMzB6tAfYwXxxk+Bm+H6HzzTWZxMTDOgtEqsP0XUDjiE2z7DrKr6LQVy9isf8Vq0A01svF+pDk0A98rgGMJuIR5uVQESF1jcD2MnVmOv8NwwGnxHpRUl1X+TrwdB6AAAA=\"}]},\"other_driver_information\":{\"information\":[{\"document_photos\":{\"pink_card\":\"UklGRnwFAABXRUJQVlA4IHAFAAAQJwCdASpwAMgAPm0uk0WkIqGYnLzEQAbEsgHYGFzs0gmfu343d4ZkTuH5Efzbp0ujvCHMxoW6/v3f3R/AP+8/zz3p/cd7T/Q8/pHoA/i/9G/Yz3lf7l6rPQA/p/nc+zj6C3lmftN8Rf7qekBmJfACujv0mcQqUvlkJRsYW4LB0hqFT8jU4/s+K5XHFfNVR7UyoPXsj6GcJlBytFU8xEznqZgPNAkk1lDce3dweb29vEu0KU1w1cSp6Vk8aUZ3rOT5tBDh8qsA5jjtSsV+UTn1Rrmtc0lweI5+dIs04Pj/YkRaxrpU972tOhDVHlBCCpZKwPeKW+4jbhZPIybDLY++0vMxcKDr9C/xR/PGi37QrNrNYOX6vHEP4J1HV5JtRiHmCxroO8C9x9HTg/3XFxddSIKjmngiVeh4wkrq9eRdt1gA/ue74iFV/MNIFvTq3l+sGQBkDeMY+9XouuHeJ116WdrUZzHk/A4j3J3VDgNKLYRaj8/gR3cbyvdsWLIbX7VbbGyfvuaVTXvX/YHNyEFWupQlnWhnACVIfOsfxSlPgEJrRRz+dslQ6MybswL0pWIahNre/RZYiFmUwTM9pvexscNQ4eHxMOVLwAAD9rX2mqaZSz6mLMmE+jWwtZofXMnZoYsMT2An8CNuQc73OyVfIs+yMz9yyIo5ZswAOqHoN1V5veto4ZQcTj7reHL0g9w17KcbZKMdnyJWnh4VjEVg8B1UxX23uOdJY+gNC4zg6G0hKJ1q7ddbEM44wo+4SiCAjqAzijEDLIAkhCdG3f70QFTBdxeAahq94rAP0DBerf5/AFrvw+j+TsMslKWC2SzMUjQvfTp08NZSASi/g0tyRehUJ5/6IuKYZABNICaWSA69fucaLSuVz78wYwHG9w9lQB7mct+pC9rogbJIxsOGmmpnjZ5E3yYs7WLkx09loTKYFMZ5l7+uK+pc6ehHLgl099gWvz2eELkhW4IBJxVdQko0LK9Ddnb14+G+/+/9QzaS71JljCB9nHCrAwHUTVEIyoVKDqkCxQBWDl33FWawl3epTHtf3HTct9BoLCcNrdwJXNM0vG46K6s/31rU6JNG5XbuvEhwOBhzdbWl+v8Pza8xO2MTLQk70LbikbvN/CPQ/c0mf1mJy/fxx4ET7zGIEuK6IZ2DlhrZ4CA3JLpGm9RnRnwC9EpY/chNleZeBotR2oaFYtan3UOXbtV4UtVt1u0S/w2hMhRbFhbDdGYpoCqwjn3muGzoQpzSAA672wLF9hSSTMplLTSR9L65hZTsaprsp4R2sXKJDaKsXTfbWLpvxB+ZhNCZlWe6ADhuLPcsP9M8Ga0d0paJLA+81+5Elsiesv8/OZQEhGEL7QUYpG8uKxZj/YxxbfRXbQ4dmzIse0LUaNMj3rVrXtTiW1vBQbw3r4J/6+wD6gDQpSPiSHAjmFbYI6H9WISbgVqHGgeNswcWql/jP0eIv7zH/wLqcZu2WS7sQR9zIUKhzODbFuAAg6zbzKNxscBtoDKiyeVl0u1iMBC53pvCAt6jOjNMFkz9Pn6R3IrhlCfwUFP+TWvwUE8yhv9kFVQ39C3rtow+Ewtfjju5wzpA1wN9k3flBRRDnXJMp613DE1NYLhw7kUzDHeknxVEtxgZ8C7E42g1b6nTOcnEthAuX3seAoP/ctKtzlugBkXCDEC7f8p202VkHwZS2JAqUy0A9hBVbFchfdP3Vp4Qkiu9aIrOjwD5RJlgYMi07wRImgMMcPxPY2pKq+FLgy4DUnIrtyh5mm4g8CRKp5xqy/vFcFdSV7GRq+kR7knJMGxvWKC/EYwm8UP+leqmM/8hMpDuH0LNdEUb2ql1y7cWkBBEYAAAAAA=\",\"drivers_license\":null,\"license_plate\":null,\"registration\":\"UklGRnwFAABXRUJQVlA4IHAFAAAQJwCdASpwAMgAPm0uk0WkIqGYnLzEQAbEsgHYGFzs0gmfu343d4ZkTuH5Efzbp0ujvCHMxoW6/v3f3R/AP+8/zz3p/cd7T/Q8/pHoA/i/9G/Yz3lf7l6rPQA/p/nc+zj6C3lmftN8Rf7qekBmJfACujv0mcQqUvlkJRsYW4LB0hqFT8jU4/s+K5XHFfNVR7UyoPXsj6GcJlBytFU8xEznqZgPNAkk1lDce3dweb29vEu0KU1w1cSp6Vk8aUZ3rOT5tBDh8qsA5jjtSsV+UTn1Rrmtc0lweI5+dIs04Pj/YkRaxrpU972tOhDVHlBCCpZKwPeKW+4jbhZPIybDLY++0vMxcKDr9C/xR/PGi37QrNrNYOX6vHEP4J1HV5JtRiHmCxroO8C9x9HTg/3XFxddSIKjmngiVeh4wkrq9eRdt1gA/ue74iFV/MNIFvTq3l+sGQBkDeMY+9XouuHeJ116WdrUZzHk/A4j3J3VDgNKLYRaj8/gR3cbyvdsWLIbX7VbbGyfvuaVTXvX/YHNyEFWupQlnWhnACVIfOsfxSlPgEJrRRz+dslQ6MybswL0pWIahNre/RZYiFmUwTM9pvexscNQ4eHxMOVLwAAD9rX2mqaZSz6mLMmE+jWwtZofXMnZoYsMT2An8CNuQc73OyVfIs+yMz9yyIo5ZswAOqHoN1V5veto4ZQcTj7reHL0g9w17KcbZKMdnyJWnh4VjEVg8B1UxX23uOdJY+gNC4zg6G0hKJ1q7ddbEM44wo+4SiCAjqAzijEDLIAkhCdG3f70QFTBdxeAahq94rAP0DBerf5/AFrvw+j+TsMslKWC2SzMUjQvfTp08NZSASi/g0tyRehUJ5/6IuKYZABNICaWSA69fucaLSuVz78wYwHG9w9lQB7mct+pC9rogbJIxsOGmmpnjZ5E3yYs7WLkx09loTKYFMZ5l7+uK+pc6ehHLgl099gWvz2eELkhW4IBJxVdQko0LK9Ddnb14+G+/+/9QzaS71JljCB9nHCrAwHUTVEIyoVKDqkCxQBWDl33FWawl3epTHtf3HTct9BoLCcNrdwJXNM0vG46K6s/31rU6JNG5XbuvEhwOBhzdbWl+v8Pza8xO2MTLQk70LbikbvN/CPQ/c0mf1mJy/fxx4ET7zGIEuK6IZ2DlhrZ4CA3JLpGm9RnRnwC9EpY/chNleZeBotR2oaFYtan3UOXbtV4UtVt1u0S/w2hMhRbFhbDdGYpoCqwjn3muGzoQpzSAA672wLF9hSSTMplLTSR9L65hZTsaprsp4R2sXKJDaKsXTfbWLpvxB+ZhNCZlWe6ADhuLPcsP9M8Ga0d0paJLA+81+5Elsiesv8/OZQEhGEL7QUYpG8uKxZj/YxxbfRXbQ4dmzIse0LUaNMj3rVrXtTiW1vBQbw3r4J/6+wD6gDQpSPiSHAjmFbYI6H9WISbgVqHGgeNswcWql/jP0eIv7zH/wLqcZu2WS7sQR9zIUKhzODbFuAAg6zbzKNxscBtoDKiyeVl0u1iMBC53pvCAt6jOjNMFkz9Pn6R3IrhlCfwUFP+TWvwUE8yhv9kFVQ39C3rtow+Ewtfjju5wzpA1wN9k3flBRRDnXJMp613DE1NYLhw7kUzDHeknxVEtxgZ8C7E42g1b6nTOcnEthAuX3seAoP/ctKtzlugBkXCDEC7f8p202VkHwZS2JAqUy0A9hBVbFchfdP3Vp4Qkiu9aIrOjwD5RJlgYMi07wRImgMMcPxPY2pKq+FLgy4DUnIrtyh5mm4g8CRKp5xqy/vFcFdSV7GRq+kR7knJMGxvWKC/EYwm8UP+leqmM/8hMpDuH0LNdEUb2ql1y7cWkBBEYAAAAAA=\"},\"document_details\":{\"first_name\":\"\",\"last_name\":\"\",\"address\":\"\",\"city\":\"\",\"province\":\"\",\"postal_code\":\"\",\"home_phone_number\":\"\",\"cell_phone_number\":\"\",\"email\":\"\",\"drivers_license_number\":\"\",\"license_plate\":\"\",\"car\":{\"year\":\"\",\"make\":\"\",\"model\":\"\"},\"policy_number\":\"\",\"insurance_company\":\"\"}}]},\"witness_information\":{\"witnesses\":[{\"name\":\"1\",\"email\":\"1@1\",\"home_phone_number\":\"111\",\"cell_phone_number\":\"1111\"},{\"name\":\"2\",\"email\":\"2@2\",\"home_phone_number\":\"2222\",\"cell_phone_number\":\"2222\"}]}}";
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
                SchemaViews.showFragment(schemaType, jsonStringBuilder.toString(), this, getSupportFragmentManager(), R.id.container);
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
            SchemaViews.startActivityToRenderConstSchema(this, jsonStringBuilder.toString(), v);

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
