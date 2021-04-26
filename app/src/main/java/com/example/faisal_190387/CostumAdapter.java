package com.example.faisal_190387;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CostumAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public static int clientIdNu;

   // DBAdapter obj;
    ArrayList<String> userName;
    ArrayList<String> email;
    ArrayList<String> lastName;
    ArrayList<String> phone;
    ArrayList<Integer> img;
    Context context;
    CostumAdapter adapter;

    public CostumAdapter(Context incomeFragment, ArrayList<String> userName, ArrayList<String> email,ArrayList<String> lastName,ArrayList<String> phone,ArrayList<Integer> img) {
        context = incomeFragment;
        this.userName = userName;
        this.email=email;
        this.lastName=lastName;
        this.phone=phone;
        this.img=img;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.adapter=this;
    }

    @Override
    public int getCount() {
        return email.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.lstlayout, null);
        Holder holder = new Holder();
        holder.txtemail =  convertView.findViewById(R.id.txtlstemail);
        holder.txtusername= convertView.findViewById(R.id.txtlstFirstName);
      //  holder.lstImage=convertView.findViewById(R.id.lstImage);
        holder.layoutMain=convertView.findViewById(R.id.layoutMain);

        holder.txtemail.setText(email.get(position));
        holder.txtusername.setText(userName.get(position));
      //  holder.lstImage.setImageResource(R.drawable.ic_user);

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ShowUserInFirebaseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FirstName",userName.get(position));
                intent.putExtra("LastName",lastName.get(position));
                intent.putExtra("Email",email.get(position));
                intent.putExtra("Phone",phone.get(position));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class Holder {
        ImageView lstImage;
        TextView txtusername,txtemail;
        LinearLayout layoutMain;
    }
}
