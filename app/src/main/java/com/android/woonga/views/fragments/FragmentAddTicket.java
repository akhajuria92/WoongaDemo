package com.android.woonga.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.woonga.R;
import com.android.woonga.adapters.AdapterImageUpload;
import com.android.woonga.response.SuccessBean;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.MyTicketsActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class FragmentAddTicket extends Fragment {
    @BindView(R.id.edt_subject)
    EditText edt_subject;

    @BindView(R.id.edt_description)
    EditText edt_description;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.imagesList)
    RecyclerView imagesList;

    int CHOOSE_IMAGE_GALLERY_REQUEST_CODE = 4;

    public static FragmentAddTicket newInstance() {
        return new FragmentAddTicket();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_tickets, container, false);
        ButterKnife.bind(this, view);
        setUi();
        setClickListeners();

        return view;

    }

    private void setClickListeners() {
        ((MyTicketsActivity) getActivity()).iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = edt_subject.getText().toString().trim();
                String description = edt_description.getText().toString().trim();
                if (subject.equalsIgnoreCase("")) {
                    Utility.getInstance().showSnackBar(mainLayout, "Please enter subject");
                } else if (description.equalsIgnoreCase("")) {
                    Utility.getInstance().showSnackBar(mainLayout, "Please enter description");
                } else {
                    addTicket(subject, description);
                }
            }
        });

        ((MyTicketsActivity) getActivity()).iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagesModelArrayList.size() < 4) {
                    attachScreenshot();
                } else {
                    Utility.getInstance().showSnackBar(mainLayout, "You can add only 4 images!");
                }
            }
        });
    }

    private void setUi() {
        ((MyTicketsActivity) getActivity()).tv_title.setText("Add Ticket");
        ((MyTicketsActivity) getActivity()).iv_attachment.setVisibility(View.VISIBLE);
        ((MyTicketsActivity) getActivity()).iv_send.setVisibility(View.VISIBLE);

        imagesList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    private void addTicket(String subject, String description) {
        String images = "";
        if (imagesModelArrayList.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                images = String.join(",", imagesModelArrayList);
            }
        }

        if (Utility.getInstance().isNetworkAvailable(getActivity())) {
            if (getActivity() != null) {
                ProgressDialog.showDialog(getActivity());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("subject", subject);
            hashMap.put("description", description);
            hashMap.put("images", images);
            RestClient.getInstance().getBaseUrl().addTicket(hashMap).enqueue(new APICallBack<SuccessBean>() {
                @Override
                protected void success(SuccessBean successBean) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                    if (successBean != null) {
                        if (successBean.getSuccess() == 1) {
                            EventBus.getDefault().postSticky(new MessageEvent("ticketAdded"));
                            if (getActivity() != null) {
                                FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.popBackStackImmediate();
                                fts.commit();
                            }
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, successBean.getMessage());
                        }
                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                }

                @Override
                protected void onFailure(String failureReason) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }
                }
            });
        } else {
            if (getActivity() != null) {
                ProgressDialog.dismissDialog(getActivity());
            }
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }
    }

    private void attachScreenshot() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                /* ... */

                if (report.areAllPermissionsGranted()) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, CHOOSE_IMAGE_GALLERY_REQUEST_CODE);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                           PermissionToken token) {
                /* ... */
                token.continuePermissionRequest();
            }
        }).check();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE_GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                        Bitmap newBitMap = BitmapFactory.decodeStream(inputStream);
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        compressImage(newBitMap, data.getData());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void compressImage(Bitmap bitmapImage, Uri uri) {
        Matrix matrix = new Matrix();
        matrix.postRotate(getImageOrientation(getRealPathFromURI(uri)));
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapImage, 0, 0, bitmapImage.getWidth(),
                bitmapImage.getHeight(), matrix, true);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byteImage = stream.toByteArray();

        uploadImage();

    }


    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private byte[] byteImage;
    String imageUrl = "";
    private ArrayList<String> imagesModelArrayList = new ArrayList<>();

    private void uploadImage() {
        if (!Utility.getInstance().isNetworkAvailable(getActivity())) {
        } else {
            android.app.ProgressDialog imageprogressdialog = new android.app.ProgressDialog(getActivity());
            imageprogressdialog.setMessage("Uploading image...");
            imageprogressdialog.setCancelable(false);
            imageprogressdialog.show();
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteImage);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", ".png", requestFile);

            RestClient.getInstance().getBaseUrl().uploadPhoto(body).enqueue(new APICallBack<JsonElement>() {
                @Override
                protected void success(JsonElement response) {
                    imageprogressdialog.dismiss();

                    if (response != null) {
                        JsonObject jObject = response.getAsJsonObject();
                        if (jObject.get("image").getAsString() != null) {
                            imageUrl = jObject.get("image").getAsString();

                            if (imageUrl != null) {
                                imagesModelArrayList.add(imageUrl);

                                if (imagesModelArrayList.size() > 0) {
                                    AdapterImageUpload adapterImageUpload = new AdapterImageUpload(getActivity(), imagesModelArrayList);
                                    imagesList.setAdapter(adapterImageUpload);
                                }

                                imageUrl = "";
                                byteImage = new byte[0];
                            }
                        }

                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    imageprogressdialog.dismiss();

                }

                @Override
                protected void onFailure(String failureReason) {
                    imageprogressdialog.dismiss();

                }


            });
        }

    }

}
