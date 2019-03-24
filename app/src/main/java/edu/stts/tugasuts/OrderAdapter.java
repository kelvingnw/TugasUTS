package edu.stts.tugasuts;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    private static RvClickListener myListener;
    private ArrayList<Order> orderlist;
    public OrderAdapter(ArrayList<Order> orderlist, RvClickListener rvcl){
        this.orderlist=orderlist;
        myListener=rvcl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v= inflater.inflate(R.layout.row_item_order,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArrayList<String>tops=new ArrayList<String>();
        tops=orderlist.get(i).getToppings();
        String topi="";
        for (String temp:tops
             ) {
            topi+=temp+" ";
        }
        viewHolder.tvQty.setText(""+orderlist.get(i).getQty()+" "+orderlist.get(i).getType());
        viewHolder.tvToppings.setText(topi);
        viewHolder.tvSubtotal.setText(""+orderlist.get(i).getSubtotal());
    }

    @Override
    public int getItemCount() {
        return (orderlist!=null)?orderlist.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQty,tvToppings,tvSubtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQty=itemView.findViewById(R.id.textView_qty_type);
            tvToppings=itemView.findViewById(R.id.textView_toppings);
            tvSubtotal=itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.recyclerViewListClick(v,ViewHolder.this.getLayoutPosition());
                }
            });

        }
    }
}
