package com.android.woonga.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.response.BankDetails;
import com.android.woonga.response.VerifyOtpResponse;
import com.android.woonga.utils.CompressionClass;
import com.android.woonga.utils.Constant;
import com.android.woonga.utils.MessageEvent;
import com.android.woonga.utils.ProgressDialog;
import com.android.woonga.utils.Utility;
import com.android.woonga.views.activities.BankDetailActivity;
import com.android.woonga.views.activities.HistoryActivity;
import com.android.woonga.views.activities.HomeActivity;
import com.android.woonga.views.activities.MainActivity;
import com.android.woonga.views.activities.MyTicketsActivity;
import com.android.woonga.views.activities.WithdrawlActivity;
import com.android.woonga.webapi.APICallBack;
import com.android.woonga.webapi.RestClient;
import com.android.woonga.webapi.WebApi;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {

    @BindView(R.id.profileView)
    RelativeLayout profileView;

    @BindView(R.id.editProfileView)
    RelativeLayout editProfileView;

    @BindView(R.id.rl_card_bank)
    RelativeLayout rl_card_bank;

    @BindView(R.id.rl_card_tickets)
    RelativeLayout rl_card_tickets;

    @BindView(R.id.rl_card_withdrawl)
    RelativeLayout rl_card_withdrawl;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvPhone)
    TextView tvPhone;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    @BindView(R.id.tv_total_points)
    TextView tv_total_points;

    @BindView(R.id.tv_pending_points)
    TextView tv_pending_points;

    @BindView(R.id.tv_approved_points)
    TextView tv_approved_points;

    @BindView(R.id.priceOut)
    TextView priceOut;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.profilePic)
    CircleImageView profilePic;

    @BindView(R.id.edit_icon)
    ImageView edit_icon;

    @BindView(R.id.mainLayout)
    NestedScrollView mainLayout;


    int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 3;
    int CHOOSE_IMAGE_GALLERY_REQUEST_CODE = 4;
    VerifyOtpResponse.Data userData;

    public static FragmentProfile newInstance() {
        return new FragmentProfile();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        setUi();

        return view;

    }


    private void setUi() {
        if (!Constant.getInstance().getProfilePic().equalsIgnoreCase("") && !Constant.getInstance().getProfilePic().equalsIgnoreCase("null")) {
            Glide.with(getActivity()).load(Constant.getInstance().getProfilePic()).into(profilePic);
        }

        if (!Constant.getInstance().getName().equalsIgnoreCase("") && !Constant.getInstance().getName().equalsIgnoreCase("null")) {
            tvName.setText(Constant.getInstance().getName());
            edtName.setText(Constant.getInstance().getName());
        }
        if (!Constant.getInstance().getPhoneNumber().equalsIgnoreCase("") && !Constant.getInstance().getPhoneNumber().equalsIgnoreCase("null")) {
            tvPhone.setText(Constant.getInstance().getPhoneNumber());
            edtPhone.setText(Constant.getInstance().getPhoneNumber());

        }
        if (!Constant.getInstance().getEmail().equalsIgnoreCase("") && !Constant.getInstance().getEmail().equalsIgnoreCase("null")) {
            tvEmail.setText(Constant.getInstance().getEmail());
            edtEmail.setText(Constant.getInstance().getEmail());
        }
        userData = new Gson().fromJson(WoongaApplication.getAppPreferences().getString(Constant.USER_DATA), VerifyOtpResponse.Data.class);

        if (userData != null) {
            if (userData.getTotalPoints() != null) {
                tv_total_points.setText("₹ " + userData.getTotalPoints());
            }
            if (userData.getPendingPoints() != null) {
                tv_pending_points.setText("₹ " + userData.getPendingPoints());
            }
            if (userData.getApprovedPoints() != null) {
                tv_approved_points.setText("₹ " + userData.getApprovedPoints());
                priceOut.setText("₹ " + userData.getApprovedPoints());
            }
        }

    }


    @OnClick(R.id.iv_edit)
    void edit() {
        profileView.setVisibility(View.GONE);
        editProfileView.setVisibility(View.VISIBLE);
        edit_icon.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_cancel)
    void cancel() {
        profileView.setVisibility(View.VISIBLE);
        editProfileView.setVisibility(View.GONE);
        edit_icon.setVisibility(View.GONE);
    }

    @OnClick(R.id.rl_card_tickets)
    void rl_card_tickets() {
        startActivity(new Intent(getActivity(), MyTicketsActivity.class));
    }

    @OnClick(R.id.rl_card_withdrawl)
    void rl_card_withdrawl() {
        startActivity(new Intent(getActivity(), WithdrawlActivity.class));
    }

    @OnClick(R.id.rl_card_bank)
    void cardBank() {

        startActivity(new Intent(getActivity(), BankDetailActivity.class));
      /*  if (rl_bankDetails.getVisibility() == View.VISIBLE) {
            rl_bankDetails.setVisibility(View.GONE);
        } else {
            rl_bankDetails.setVisibility(View.VISIBLE);
            mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final int scrollViewHeight = mainLayout.getHeight();
                    if (scrollViewHeight > 0) {
                        mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        final View lastView = mainLayout.getChildAt(mainLayout.getChildCount() - 1);
                        final int lastViewBottom = lastView.getBottom() + mainLayout.getPaddingBottom();
                        final int deltaScrollY = lastViewBottom - scrollViewHeight - mainLayout.getScrollY();
                        *//* If you want to see the scroll animation, call this. *//*
                        mainLayout.smoothScrollBy(0, deltaScrollY);
                    }
                }
            });
        }*/
    }

    @OnClick(R.id.edit_icon)
    void edit_icon() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_photo);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals(getString(R.string.take_pic))) {
                    Dexter.withActivity(getActivity())
                            .withPermissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            ).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            /* ... */

                            if (report.areAllPermissionsGranted()) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                                       PermissionToken token) {
                            /* ... */
                            token.continuePermissionRequest();
                        }
                    }).check();
                } else if (options[item].equals(getString(R.string.choose_photo))) {

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

                } else if (options[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byteImage = stream.toByteArray();

                uploadImage();
            }

        } else if (requestCode == CHOOSE_IMAGE_GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                        Bitmap newBitMap = BitmapFactory.decodeStream(inputStream);
                        //createFileFromBitmap(getAspectRationBitmap(newBitMap, IMAGE_WIDTH, IMAGE_HEIGHT), dirPath, fileName);
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

    private byte[] byteImage;

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

    String profilePicUrl = "";

    private void uploadImage() {
        if (!Utility.getInstance().isNetworkAvailable(getActivity())) {
        } else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteImage);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", ".png", requestFile);

            RestClient.getInstance().getBaseUrl().uploadPhoto(body).enqueue(new APICallBack<JsonElement>() {
                @Override
                protected void success(JsonElement response) {
                    if (response != null) {
                        JsonObject jObject = response.getAsJsonObject();
                        if (jObject.get("image").getAsString() != null) {
                            profilePicUrl = jObject.get("image").getAsString();

                            Glide.with(FragmentProfile.this)
                                    .load(profilePicUrl)
                                    .into(profilePic);
                        }

                    }
                }

                @Override
                protected void failure(String errorMsg) {
                    if (getActivity() != null) {
                        com.android.woonga.utils.ProgressDialog.dismissDialog(getActivity());
                    }
                }

                @Override
                protected void onFailure(String failureReason) {
                    if (getActivity() != null) {
                        com.android.woonga.utils.ProgressDialog.dismissDialog(getActivity());
                    }
                }


            });
        }

    }

    @OnClick(R.id.btn_save)
    void save() {
        if (edtName.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter name");
        } else if (edtEmail.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter email");

        } else if (edtPhone.getText().toString().equalsIgnoreCase("")) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter phone");

        } else if (!Utility.getInstance().isValidEmail(edtEmail.getText().toString())) {
            Utility.getInstance().showSnackBar(mainLayout, "Please enter valid email");
        } else {
            updateProfile();
        }
    }


    private void updateProfile() {
        Utility.getInstance().hideKeyBoard(getActivity());
        if (Utility.getInstance().isNetworkAvailable(getActivity())) {

            ProgressDialog.showDialog(getActivity());

            HashMap hashMap = new HashMap();
            hashMap.put("security_token", Constant.getInstance().getSecurityToken());
            hashMap.put("name", edtName.getText().toString());
            hashMap.put("email", edtEmail.getText().toString());
            hashMap.put("phoneNumber", edtPhone.getText().toString());
            hashMap.put("profile_pic", profilePicUrl);
            RestClient.getInstance().getBaseUrl().updateProfile(hashMap).enqueue(new APICallBack<VerifyOtpResponse>() {
                @Override
                protected void success(VerifyOtpResponse response) {
                    if (getActivity() != null) {
                        ProgressDialog.dismissDialog(getActivity());
                    }

                    if (response != null) {
                        if (response.getData() != null) {
                            WoongaApplication.getAppPreferences().putString(Constant.USER_DATA, new Gson().toJson(response.getData()));
                            EventBus.getDefault().postSticky(new MessageEvent("UserDataUpdated"));
                        }
                        if (response.getSuccess().equals(Constant.FLAG_TRUE)) {
                            profileView.setVisibility(View.VISIBLE);
                            editProfileView.setVisibility(View.GONE);
                            edit_icon.setVisibility(View.GONE);
                            setUi();
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                        } else if (response.getSuccess().equals(2)) {
                            profileView.setVisibility(View.VISIBLE);
                            editProfileView.setVisibility(View.GONE);
                            edit_icon.setVisibility(View.GONE);
                            setUi();
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
                        } else if (response.getSuccess().equals(3)) {
                            profileView.setVisibility(View.VISIBLE);
                            editProfileView.setVisibility(View.GONE);
                            edit_icon.setVisibility(View.GONE);
                            setUi();
                            FragmentVerifyNewNumber fragmentVerifyNewNumber = FragmentVerifyNewNumber.newInstance();
                            FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("phoneNumber", response.getNew_phone_number());
                            bundle.putString("otp", response.getOtp());
                            fragmentVerifyNewNumber.setArguments(bundle);
                            fts.replace(R.id.fragmentHolder, fragmentVerifyNewNumber);
                            fts.addToBackStack(fragmentVerifyNewNumber.getClass().getSimpleName());
                            fts.commit();
                        } else {
                            Utility.getInstance().showSnackBar(mainLayout, response.getMessage());
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
            Utility.getInstance().showSnackBar(mainLayout, "Please check your Internet Connection");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick(R.id.iv_settings)
    void settings() {
        FragmentSettings fragmentSettings = FragmentSettings.newInstance();
        FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.fragmentHolder, fragmentSettings);
        fts.addToBackStack(fragmentSettings.getClass().getSimpleName());
        fts.commit();
       // ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.ll_points)
    void pointsClick() {
        startActivity(new Intent(getActivity(), HistoryActivity.class));
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {

        if (event.message.contains("PhoneUpdated")) {
            EventBus.getDefault().removeStickyEvent(event);
            setUi();
        }

        if (event.message.contains("UserDataUpdated")) {
            EventBus.getDefault().removeStickyEvent(event);
            setUi();
        }
    }
}
