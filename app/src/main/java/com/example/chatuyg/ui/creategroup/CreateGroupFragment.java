package com.example.chatuyg.ui.creategroup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.chatuyg.GroupModel;
import com.example.chatuyg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CreateGroupFragment extends Fragment {
    TextView groupName,groupDescription;
    Button createGroup;
    RecyclerView groupRv;
    ImageView groupImage;

    Uri filePath;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseStorage mStorage;

    ArrayList<GroupModel> groupModelArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.activity_create_group_fragment, container,false);
        groupRv=root.findViewById(R.id.recyclerView_create_group);
        groupDescription=root.findViewById(R.id.textView_groupDescription);
        groupName=root.findViewById(R.id.textView_groupName);
        createGroup=root.findViewById(R.id.create_group);
        groupImage=root.findViewById(R.id.imageView_group_ımage);
        mAuth=FirebaseAuth.getInstance();
        mStorage=FirebaseStorage.getInstance();
        mStore=FirebaseFirestore.getInstance();

        groupModelArrayList=new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode()==getActivity().RESULT_OK){
                Intent data=result.getData();
                filePath=data.getData();
                groupImage.setImageURI(filePath);
            }
        });

        groupImage.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });
        createGroup.setOnClickListener(view -> {
            String name=groupName.getText().toString();
            String description=groupDescription.getText().toString();

            if(name.isEmpty()){
                Toast.makeText(getContext(),"Grup ismi gerekli",Toast.LENGTH_SHORT).show();
                return;
            }
            if(description.isEmpty()){
                Toast.makeText(getContext(),"Grup açıklaması gerekli",Toast.LENGTH_SHORT).show();
                return;
            }
            if(filePath!=null){
                StorageReference storageReference=mStorage.getReference().child("/resimler" + UUID.randomUUID().toString());
                storageReference.putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl=uri.toString();
                        Toast.makeText(getContext(),"Resim yüklendi",Toast.LENGTH_SHORT).show();
                        CreateGroup(name,description,downloadUrl);
                    });
                });
                return;
            }
            Toast.makeText(getContext(),"Group created succesfully",Toast.LENGTH_SHORT).show();
        });

        FetchGroup();

        return root;
    }
    private void CreateGroup(String name,String description,String image){
        String userId=mAuth.getCurrentUser().getUid();
        mStore.collection("/userdata/"+userId+"/"+"groups").add(new HashMap<String, Object>(){{
            put("name",name);
            put("description",description);
            put("image",image);
            put("numbers",new ArrayList<String>());
        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(),"Grup oluşturuldu",Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                GroupModel groupModel=documentSnapshot.toObject(GroupModel.class);
                groupModelArrayList.add(groupModel);
                groupRv.getAdapter().notifyDataSetChanged();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(),"Grup oluşturulamadı",Toast.LENGTH_SHORT).show();
        });
    }
    private  void FetchGroup(){
        String userId=mAuth.getCurrentUser().getUid();

        mStore.collection("/userdata/"+userId+"/"+"groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            groupModelArrayList.clear();
            for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                GroupModel groupModel=new GroupModel(documentSnapshot.getString("name"),documentSnapshot.getString("description"),documentSnapshot.getString("image"),(List<String>) documentSnapshot.get("numbers"),documentSnapshot.getId());
                groupModelArrayList.add(groupModel);

                groupRv.setAdapter(new GroupAdapter(groupModelArrayList));
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                groupRv.setLayoutManager(linearLayoutManager);
            }
        });
    }
}