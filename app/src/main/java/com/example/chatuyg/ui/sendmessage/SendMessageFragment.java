package com.example.chatuyg.ui.sendmessage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chatuyg.GroupModel;
import com.example.chatuyg.MessageModel;
import com.example.chatuyg.R;
import com.example.chatuyg.ui.addtogroup.GroupAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SendMessageFragment extends Fragment {
    RecyclerView groupsRv,messagesRv;
    TextView selectedgroupTv,selectedmessageTv;
    Button sendButton;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    ArrayList<GroupModel> groupModelArrayList;
    ArrayList<MessageModel> messageModelArrayList;

    GroupModel selectedGroup;
    MessageModel selectedMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.activity_send_message_fragment, container, false);

        groupsRv=root.findViewById(R.id.sendmessage_groupRecysclerView);
        messagesRv=root.findViewById(R.id.sendmessage_messagesRecyclerView);
        selectedgroupTv=root.findViewById(R.id.sendmessage_selectedGroupTextView);
        selectedmessageTv=root.findViewById(R.id.sendmessage_selectedmessage);
        sendButton=root.findViewById(R.id.sendmessage_sendbutton);

        mAuth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();

        groupModelArrayList=new ArrayList<>();
        messageModelArrayList=new ArrayList<>();

        FetchFroup();
        FetchMessage();
        return root;
    }
    private void FetchFroup(){
        String userId=mAuth.getCurrentUser().getUid();
        mStore.collection("/userdata/"+userId+"/groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            groupModelArrayList.clear();
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                GroupModel groupModel=new GroupModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getString("image"),(List<String>) documentSnapshot.get("numbers"),documentSnapshot.getId());
                groupModelArrayList.add(groupModel);
            }
            groupsRv.setAdapter(new GroupAdapter(groupModelArrayList, position -> {
                selectedGroup=groupModelArrayList.get(position);
                selectedgroupTv.setText("Seçili Grup: "+selectedGroup.getName());
            }));
            LinearLayoutManager linearLayout=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
            groupsRv.setLayoutManager(linearLayout);
        });
    }
    private void FetchMessage(){
        String userId=mAuth.getCurrentUser().getUid();

        mStore.collection("/userdata/"+userId+"/"+"messages").get().addOnSuccessListener(queryDocumentSnapshots -> {
            messageModelArrayList.clear();
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                MessageModel messageModel=new MessageModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getId());
                messageModelArrayList.add(messageModel);
            }
            messagesRv.setAdapter(new SendMessageAdapter(messageModelArrayList, position -> {
                selectedMessage=messageModelArrayList.get(position);
                selectedmessageTv.setText("Seçili mesaj: "+selectedMessage.getName());
            }));
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            messagesRv.setLayoutManager(linearLayoutManager);
        });
    }
}