package edu.stts.tugasuts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private EditText edName;
    private RadioGroup rgType;
    private RadioButton rbTea,rbCoffee,rbSmoothies;
    private CheckBox cbPearl,cbPudding,cbRedBean,cbCoconut;
    private Button btnMinus,btnPlus,btnAdd,btnEdit,btnDelete,btnReset;
    private TextView txtQty,txtTotal,txtName;
    private RecyclerView rvOrder;
    private RadioButton rbPil;
    private OrderAdapter adapter;
    private ArrayList<Order> arrOrder= new ArrayList<>();
    private long total=0;
    private int index=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edName=(EditText) findViewById(R.id.editText_name);
        rgType=findViewById(R.id.radioGroup_type);
        rbTea=findViewById(R.id.radioButton_Tea);
        rbCoffee=findViewById(R.id.radioButton_Coffee);
        rbSmoothies=findViewById(R.id.radioButton_Smoothies);
        cbPearl=findViewById(R.id.checkBox_pearl);
        cbPudding=findViewById(R.id.checkBox_pudding);
        cbRedBean=findViewById(R.id.checkBox_red_bean);
        cbCoconut=findViewById(R.id.checkBox_coconut);
        txtQty=findViewById(R.id.textView_qty);
        btnMinus=findViewById(R.id.button_minus);
        btnPlus=findViewById(R.id.button_plus);
        btnDelete=findViewById(R.id.button_delete);
        btnAdd=findViewById(R.id.button_add);
        btnReset=findViewById(R.id.button_reset);
        rvOrder=findViewById(R.id.recyclerview_order);
        txtName=findViewById(R.id.textView_name);
        txtTotal=findViewById(R.id.textView_total);
        adapter=new OrderAdapter(arrOrder, new RvClickListener() {
            @Override
            public void recyclerViewListClick(View v, int posisi) {
                String Types=arrOrder.get(posisi).getType();
                refresh();
                rbTea.setChecked(false);
                rbCoffee.setChecked(false);
                rbSmoothies.setChecked(false);
                if(Types=="Tea"){
                    rbTea.setChecked(true);
                }
                else if(Types=="Coffee"){
                    rbCoffee.setChecked(true);
                }
                else if(Types=="Smoothies"){
                    rbSmoothies.setChecked(true);
                }
                ArrayList<String> temptoppings= new ArrayList<String>();
                temptoppings=arrOrder.get(posisi).getToppings();
                for (String top:temptoppings) {
                    if(top.equalsIgnoreCase("Pearl")){
                        cbPearl.setChecked(true);
                    }
                    if(top.equalsIgnoreCase("Pudding")){
                        cbPudding.setChecked(true);
                    }
                    if(top.equalsIgnoreCase("Coconut")){
                        cbCoconut.setChecked(true);
                    }
                    if(top.equalsIgnoreCase("Red Bean")){
                        cbRedBean.setChecked(true);
                    }
                }
                int tempqty=arrOrder.get(posisi).getQty();
                txtQty.setText(""+tempqty);
            }
        });
        total=0;

        RecyclerView.LayoutManager lm= new LinearLayoutManager(MainActivity.this);
        rvOrder.setLayoutManager(lm);
        rvOrder.setAdapter(adapter);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtytemp= Integer.parseInt(String.valueOf(txtQty.getText()));
                if(qtytemp>1){
                    qtytemp-=1;
                    txtQty.setText(""+qtytemp);
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qtytemp= Integer.parseInt(String.valueOf(txtQty.getText()));
                qtytemp=qtytemp+1;
                txtQty.setText(""+qtytemp);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrOrder=new ArrayList<Order>();
                int subtotal=0;
                int chosen = rgType.getCheckedRadioButtonId();
                rbPil=(RadioButton)(findViewById(chosen));
                String tipe=rbPil.getText().toString();
                if(tipe.equalsIgnoreCase("Tea")){
                    subtotal+=23000;
                }
                else if(tipe.equalsIgnoreCase("Coffee")){
                    subtotal+=25000;
                }
                else if(tipe.equalsIgnoreCase("Smoothies")){
                    subtotal+=30000;
                }
                ArrayList<String> toppings= new ArrayList<String>();
                if(cbPearl.isChecked()){
                    toppings.add("Pearl");
                    subtotal+=3000;
                }
                if(cbPudding.isChecked()){
                    toppings.add("Pudding");
                    subtotal+=3000;
                }
                if(cbRedBean.isChecked()){
                    toppings.add("Red Bean");
                    subtotal+=4000;
                }
                if(cbCoconut.isChecked()){
                    toppings.add("Coconut");
                    subtotal+=4000;
                }

                arrOrder.add(new Order(rbPil.getText().toString(),toppings,
                        Integer.parseInt(String.valueOf(txtQty.getText())),subtotal));
                txtName.setText(edName.getText().toString());
                adapter.notifyDataSetChanged();
                rvOrder.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),""+arrOrder.get(0).getType(),Toast.LENGTH_SHORT).show();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edName.setText("");
                txtName.setText("Cust");
                rgType.clearCheck();
                rbTea.setChecked(true);
                cbPearl.setChecked(false);
                cbCoconut.setChecked(false);
                cbRedBean.setChecked(false);
                cbPudding.setChecked(false);
                txtQty.setText("1");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean deleter=false;
                int chosen = rgType.getCheckedRadioButtonId();
                rbPil=(RadioButton)(findViewById(chosen));
                ArrayList<String> toppings= new ArrayList<String>();
                if(cbPearl.isChecked()){
                    toppings.add("Pearl");
                }
                if(cbPudding.isChecked()){
                    toppings.add("Pudding");
                }
                if(cbRedBean.isChecked()){
                    toppings.add("Red Bean");
                }
                if(cbCoconut.isChecked()){
                    toppings.add("Coconut");
                }
                int posisi=0;

                for (Order ord:arrOrder) {
                    if(ord.getType().equalsIgnoreCase(rbPil.getText().toString()) &&
                            (ord.getQty() == Integer.parseInt(String.valueOf(txtQty.getText()))))
                    {
                        ArrayList<String> temptoppings= new ArrayList<String>();
                        temptoppings=ord.getToppings();
                        if(toppings.equals(temptoppings)){
                            deleter=true;
                        }
                    }
                    if(!deleter){
                        posisi++;
                    }
                }
                if(deleter){
                    arrOrder.remove(posisi);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
    private void refresh(){
        rgType.clearCheck();
        rbTea.setChecked(true);
        cbPearl.setChecked(false);
        cbCoconut.setChecked(false);
        cbRedBean.setChecked(false);
        cbPudding.setChecked(false);
        txtQty.setText("1");
    }

}
