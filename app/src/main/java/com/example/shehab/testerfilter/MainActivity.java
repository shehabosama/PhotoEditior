package com.example.shehab.testerfilter;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shehab.testerfilter.Interface.AddTextFragmentListener;
import com.example.shehab.testerfilter.Interface.Add_frame_listener;
import com.example.shehab.testerfilter.Interface.BrushFragmentListener;
import com.example.shehab.testerfilter.Interface.EmojiFragmentListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

import com.example.shehab.testerfilter.utils.BitmapUtils;

public class MainActivity extends AppCompatActivity implements FiltersListFragment.FiltersListFragmentListener, EditImageFragment.EditImageFragmentListener, BrushFragmentListener, EmojiFragmentListener, AddTextFragmentListener, Add_frame_listener {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String IMAGE_NAME = "dog.jpg";
    private static final int CAMERA_REQUEST =102 ;

    PhotoEditor photoEditor;
     String path;


    private Bitmap sourceBitmap;
    private Canvas sourceCanvas = new Canvas();
    private Paint destPaint = new Paint();
    private Path destPath = new Path();

    public static final int SELECT_GALLERY_IMAGE = 101;
    public static final int SELECT_INSERT_IMAGE = 100;

    CardView btn_filter_list,btn_edit,btn_brush,btn_emoji,btn_text,btn_add_image,btn_add_frame,btn_crop;

    @BindView(R.id.image_preview)
    PhotoEditorView photoEditorView;


    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    Bitmap originalImage;
    // to backup image with filter applied
    Bitmap filteredImage;

    // the final image after applying
    // brightness, saturation, contrast
    Bitmap finalImage;

    FiltersListFragment filtersListFragment;
    EditImageFragment editImageFragment;

    // modified image values
    int brightnessFinal = 0;
    float saturationFinal = 1.0f;
    float contrastFinal = 1.0f;

    Uri imageCrop;

    // load native image filters library
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.activity_title_main));


        photoEditor = new PhotoEditor.Builder(this,photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultEmojiTypeface(Typeface.createFromAsset(getAssets(),"emojione-android.ttf"))
                .build();



        btn_filter_list = (CardView)findViewById(R.id.btn_filter_list);
        btn_edit = (CardView)findViewById(R.id.btn_filter_edit);
        btn_brush = (CardView)findViewById(R.id.btn_brush);
        btn_emoji = (CardView)findViewById(R.id.btn_emoji);
        btn_text = (CardView)findViewById(R.id.btn_text);
        btn_add_image = (CardView)findViewById(R.id.btn_image);
        btn_add_frame = (CardView)findViewById(R.id.btn_frame);
        btn_crop = (CardView)findViewById(R.id.btn_crop);


        btn_filter_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(filtersListFragment != null)
                {

                    filtersListFragment.show(getSupportFragmentManager(),filtersListFragment.getTag());
                }
                else
                {

                    FiltersListFragment filtersListFragment2 = FiltersListFragment.getInstance(null);
                    filtersListFragment2.setListener(MainActivity.this);
                    filtersListFragment2.show(getSupportFragmentManager(),filtersListFragment2.getTag());
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditImageFragment editImageFragment = EditImageFragment.getInstance();
                editImageFragment.setListener(MainActivity.this);
                editImageFragment.show(getSupportFragmentManager(),editImageFragment.getTag());
            }
        });

        btn_brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                photoEditor.setBrushDrawingMode(true);

                BrushFragment brushFragment = BrushFragment.getInstance();
                brushFragment.setListener(MainActivity.this);
                brushFragment.show(getSupportFragmentManager(),brushFragment.getTag());
            }
        });

        btn_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmojiFragment emojiFragment = EmojiFragment.getInstance();
                emojiFragment.setListener(MainActivity.this);
                emojiFragment.show(getSupportFragmentManager(),emojiFragment.getTag());

            }
        });
        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTExtFragment addTExtFragment = AddTExtFragment.getInstance();
                addTExtFragment.setListener(MainActivity.this);
                addTExtFragment.show(getSupportFragmentManager(),addTExtFragment.getTag());
            }
        });


        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImagetoPicture();
            }
        });

        btn_add_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameFragment frameFragment = FrameFragment.getInstance();
                frameFragment.setListener(MainActivity.this);
                frameFragment.show(getSupportFragmentManager(),frameFragment.getTag());
            }
        });



        btn_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartCrop(imageCrop);
            }
        });

        loadImage();




    }

    private void StartCrop(Uri uri)
    {
        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
        UCrop uCrop = UCrop.of(uri,Uri.fromFile(new File(getCacheDir(),destinationFileName)));
        uCrop.start(MainActivity.this);


    }

    private void addImagetoPicture() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {



                           Intent intent = new Intent(Intent.ACTION_PICK);
                           intent.setType("image/*");
                           startActivityForResult(intent,SELECT_INSERT_IMAGE);



                        } else {
                            Toast.makeText(getApplicationContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // adding filter list fragment
        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListener(this);

        // adding edit image fragment
        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, getString(R.string.tab_filters));
        adapter.addFragment(editImageFragment, getString(R.string.tab_edit));

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        // reset image controls
      //  resetControls();

        // applying the selected filter
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        photoEditorView.getSource().setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }

    @Override
    public void onBrightnessChanged(final int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        photoEditorView.getSource().setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onSaturationChanged(final float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        photoEditorView.getSource().setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onContrastChanged(final float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        photoEditorView.getSource().setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
        // once the editing is done i.e seekbar is drag is completed,
        // apply the values on to filtered image
        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        finalImage = myFilter.processFilter(bitmap);
    }

    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    @Override
    public void onBrushSizeChangeListener(float Size) {
        photoEditor.setBrushSize(Size);
    }

    @Override
    public void onBrushOpacityChangeListener(int opactiy) {

        photoEditor.setOpacity(opactiy);
    }

    @Override
    public void onBrushColorChangeListener(int color) {

        photoEditor.setBrushColor(color);
    }

    @Override
    public void onBrushStateChangeListener(boolean isEraser) {

        if(isEraser)
            photoEditor.brushEraser();
        else
            photoEditor.setBrushDrawingMode(true);
    }

    @Override
    public void EmojiSelected(String emoji) {
        photoEditor.addEmoji(emoji);
    }

    @Override
    public void onAddTextButtonClick(Typeface typeface, String text, int color) {
        photoEditor.addText(typeface,text,color);
    }

    @Override
    public void onAddFrame(int frame)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),frame);

        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,100,100,true);
        photoEditor.addImage(bitmap1);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    // load the default image from assets on app launch
    private void loadImage() {
        originalImage = BitmapUtils.getBitmapFromAssets(this, IMAGE_NAME, 300, 300);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        photoEditorView.getSource().setImageBitmap(originalImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_open) {
            openImageFromGallery();
            return true;
        }

        if (id == R.id.action_save) {
            saveImageToGallery();
            return true;
        }
        if (id == R.id.action_Camera) {
            openCamera();
            return true;
        }

        if (id == R.id.action_about) {
            Intent toAbout_intent = new Intent(getApplicationContext(),About_Activity.class);
            startActivity(toAbout_intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openCamera()
    {
        Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE,"New Pictures");
                            values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera");
                            imageCrop = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                    values);
                            Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageCrop);
                            startActivityForResult(CameraIntent,CAMERA_REQUEST);
                        } else {
                            Toast.makeText(getApplicationContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ) {
            if(requestCode == SELECT_GALLERY_IMAGE) {
                Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 800, 800);

                imageCrop = data.getData();

                // clear bitmap memory
                originalImage.recycle();
                finalImage.recycle();
                finalImage.recycle();

                originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
                finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
                photoEditorView.getSource().setImageBitmap(originalImage);
                bitmap.recycle();







                filtersListFragment = FiltersListFragment.getInstance(originalImage);
                filtersListFragment.setListener(this);
            }

            if(requestCode == CAMERA_REQUEST) {
                Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, imageCrop, 800, 800);


                // clear bitmap memory
                originalImage.recycle();
                finalImage.recycle();
                finalImage.recycle();

                originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
                filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
                photoEditorView.getSource().setImageBitmap(originalImage);
                bitmap.recycle();



                filtersListFragment = FiltersListFragment.getInstance(originalImage);
                filtersListFragment.setListener(this);
            }

            else if(requestCode == SELECT_INSERT_IMAGE)
            {
                Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this,data.getData(),100,100);
                photoEditor.addImage(bitmap);
            }else if (requestCode == UCrop.REQUEST_CROP)
            {
                handelCropReqest(data);
            }else if(requestCode == UCrop.RESULT_ERROR)
            {
                hendelCropError(data);
            }
        }
    }

    private void hendelCropError(Intent data)
    {
        final Throwable cropError = UCrop.getError(data);
        if(cropError==null)
        {
            Toast.makeText(this, ""+cropError.getMessage(), Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "unExpected Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void handelCropReqest(Intent data)
    {

        final Uri resultUri = UCrop.getOutput(data);
        if(resultUri !=null)
        {
            photoEditorView.getSource().setImageURI(resultUri);

            Bitmap bitmap = ((BitmapDrawable)photoEditorView.getSource().getDrawable()).getBitmap();

            originalImage =bitmap.copy(Bitmap.Config.ARGB_8888,true);
            filteredImage = originalImage;
            finalImage = originalImage;
        }else
        {
            Toast.makeText(this, "can't retrieve crop", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImageFromGallery() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, SELECT_GALLERY_IMAGE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /*
    * saves image to camera gallery
    * */
    private void saveImageToGallery() {
        Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            photoEditor.saveAsBitmap(new OnSaveBitmap() {
                                @Override
                                public void onBitmapReady(Bitmap saveBitmap) {

                                    photoEditorView.getSource().setImageBitmap(saveBitmap);

                                     path = BitmapUtils.insertImage(getContentResolver(), saveBitmap, System.currentTimeMillis()
                                            + "_profile.jpg", null);



                                    if (!TextUtils.isEmpty(path)) {
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Image saved to gallery!", Snackbar.LENGTH_LONG)
                                                .setAction("OPEN", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        openImage(path);
                                                    }
                                                });

                                        snackbar.show();
                                    } else {
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Unable to save image!", Snackbar.LENGTH_LONG);

                                        snackbar.show();
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Permissions are not granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    // opening image in default image viewer app
    private void openImage(String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(path), "image/*");
        startActivity(intent);
    }









    public class MyCustomeView extends View
    {
        private Bitmap sourceBitmap;
        private Canvas sourceCanvas = new Canvas();
        private Paint destPaint = new Paint();
        private Path destPath = new Path();


        public MyCustomeView(Context context)
        {
            super(context);
            Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chin_1);

            //converting bitmap into mutable bitmap
            sourceBitmap = Bitmap.createBitmap(rawBitmap.getWidth(), rawBitmap.getHeight(), Bitmap.Config.ARGB_8888);


            //converting bitmap into mutable bitmap
            sourceBitmap = Bitmap.createBitmap(rawBitmap.getWidth(), rawBitmap.getHeight(), Bitmap.Config.ARGB_8888);

            sourceCanvas.setBitmap(sourceBitmap);
            sourceCanvas.drawBitmap(rawBitmap, 0, 0, null);

            destPaint.setAlpha(0);
            destPaint.setAntiAlias(true);
            destPaint.setStyle(Paint.Style.STROKE);
            destPaint.setStrokeJoin(Paint.Join.ROUND);
            destPaint.setStrokeCap(Paint.Cap.ROUND);
            //change this value as per your need
            destPaint.setStrokeWidth(50);
            destPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            sourceCanvas.drawPath(destPath, destPaint);
            canvas.drawBitmap(sourceBitmap, 0, 0, null);
            super.onDraw(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            float xPos = event.getX();
            float yPos = event.getY();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    destPath.moveTo(xPos, yPos);
                    break;

                case MotionEvent.ACTION_MOVE:
                    destPath.lineTo(xPos, yPos);
                    break;

                default:
                    return false;
            }

            invalidate();
            return true;
        }
    }

}
