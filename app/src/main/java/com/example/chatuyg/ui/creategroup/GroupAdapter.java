package com.example.chatuyg.ui.creategroup;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chatuyg.GroupModel;
import com.example.chatuyg.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    List<GroupModel> groupModelList;
    public GroupAdapter (List<GroupModel> groupModelList){
        this.groupModelList=groupModelList;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupViewHolder groupViewHolder=new GroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_creategroup_group,parent,false));
        return groupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupModel groupModel=groupModelList.get(position);
        holder.setData(groupModel);
    }

    @Override
    public int getItemCount() {
        return groupModelList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView groupName,groupDesctription;
        ImageView groupImageView;
        public GroupViewHolder(View itemView){
            super(itemView);
            groupImageView=itemView.findViewById(R.id.imageView);
            groupName=itemView.findViewById(R.id.group_name);
            groupDesctription=itemView.findViewById(R.id.group_description);
        }
        public void setData(GroupModel groupModel){
            groupName.setText(groupModel.getName());
            groupDesctription.setText(groupModel.getDescription());
            if(GroupModel.getImage() != null){
                Picasso.get().load(groupModel.getImage()).into(groupImageView);
            }
        }
    }
}