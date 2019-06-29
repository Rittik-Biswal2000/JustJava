package com.example.justjava;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disableAutofill();
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutofill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }

    /**
     * This method displays the given text on the screen.
     */
    /**
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }
**/
    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0;

    public void submitOrder(View view) {


        int price = calculatePrice();
        EditText namefield = findViewById(R.id.name);
        String iname = namefield.getText().toString();
        CheckBox WhippedCreamCheckBox = findViewById(R.id.checkBox);
        boolean hasWhippedCheckBox = WhippedCreamCheckBox.isChecked();
        CheckBox ChocolateCheckBox = findViewById(R.id.creamcheckBox);
        boolean isChocolateCheckBox = ChocolateCheckBox.isChecked();

        String priceMessage = createOrderSummary(price, hasWhippedCheckBox, isChocolateCheckBox, iname);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for "+iname);
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        /**displayMessage(priceMessage);**/


    }

    public int calculatePrice() {
        int price = quantity * 10;
        return price;

    }

    public String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String iname) {
        String priceMessage = "Name: " + iname + "\n";

        priceMessage += "Add WhippedCream? " + addWhippedCream;
        if (addWhippedCream == true) {
            price += quantity * 2;
        }
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        if (addChocolate == true) {
            price += quantity * 1;

        }
        priceMessage = priceMessage + "\nQuantity " + quantity + "\n";
        priceMessage = priceMessage + "Total : ₹" + price + "\n";
        priceMessage += "Thank You!" + "\n";
        return priceMessage;
    }

    public void increment(View view) {
        if (quantity < 100){
            quantity++;
        display(quantity);
    }

}


    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
            display(quantity);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     *
     * @param number
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
