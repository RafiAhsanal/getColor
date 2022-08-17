package com.rafi.getcolor;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public final int REQ_CD_FILE = 101;
    private final double red = 0;
    private final double green = 0;
    private final double blue = 0;
    private final ArrayList<String> listString = new ArrayList<>();
    private final Intent file = new Intent(Intent.ACTION_GET_CONTENT);
    private boolean alpha = false;
    private String colorPicked = "";
    private LinearLayout line_alpha;
    private LinearLayout bg_color;
    private ImageView img_color;
    private TextView tv_color;
    private LinearLayout btn_vibrant;
    private LinearLayout btn_vibrantlight;
    private LinearLayout btn_vibrantdark;
    private LinearLayout btn_muted;
    private LinearLayout btn_mutedlight;
    private LinearLayout btn_muteddark;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        initialize();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            initializeLogic();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize() {

        line_alpha = findViewById(R.id.line_alpha);
        bg_color = findViewById(R.id.bg_color);
        ImageView img_back = findViewById(R.id.img_back);
        CheckBox checkbox = findViewById(R.id.checkbox);
        img_color = findViewById(R.id.img_color);
        tv_color = findViewById(R.id.tv_color);
        btn_vibrant = findViewById(R.id.btn_vibrant);
        btn_vibrantlight = findViewById(R.id.btn_vibrantlight);
        btn_vibrantdark = findViewById(R.id.btn_vibrantdark);
        btn_muted = findViewById(R.id.btn_muted);
        btn_mutedlight = findViewById(R.id.btn_mutedlight);
        btn_muteddark = findViewById(R.id.btn_muteddark);
        Button btn_set = findViewById(R.id.btn_set);
        file.setType("image/*");
        file.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        img_back.setOnClickListener(_view -> finish());

        checkbox.setOnCheckedChangeListener((_param1, _param2) -> {
            final boolean _isChecked = _param2;
            alpha = _isChecked;
        });

        btn_vibrant.setOnClickListener(_view -> {
            Toast toast1 = Toast.makeText(getApplicationContext(), _getColorView(btn_vibrant, alpha), Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.CENTER, 0, 0);

            toast1.show();
            ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _getColorView(btn_vibrant, alpha)));
        });

        btn_vibrantlight.setOnClickListener(_view -> {
            Toast toast1 = Toast.makeText(getApplicationContext(), _getColorView(btn_vibrantlight, alpha), Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.CENTER, 0, 0);

            toast1.show();
            ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _getColorView(btn_vibrantlight, alpha)));
        });

        btn_vibrantdark.setOnClickListener(_view -> {
            Toast toast1 = Toast.makeText(getApplicationContext(), _getColorView(btn_vibrantdark, alpha), Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.CENTER, 0, 0);

            toast1.show();
            ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _getColorView(btn_vibrantdark, alpha)));
        });

        btn_muted.setOnClickListener(_view -> {
            Toast toast1 = Toast.makeText(getApplicationContext(), _getColorView(btn_muted, alpha), Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.CENTER, 0, 0);

            toast1.show();
            ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _getColorView(btn_muted, alpha)));
        });

        btn_mutedlight.setOnClickListener(_view -> {
            Toast toast1 = Toast.makeText(getApplicationContext(), _getColorView(btn_mutedlight, alpha), Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.CENTER, 0, 0);

            toast1.show();
            ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _getColorView(btn_mutedlight, alpha)));
        });

        btn_muteddark.setOnClickListener(_view -> {
            Toast toast1 = Toast.makeText(getApplicationContext(), _getColorView(btn_muteddark, alpha), Toast.LENGTH_SHORT);

            toast1.setGravity(Gravity.CENTER, 0, 0);

            toast1.show();
            ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", _getColorView(btn_muteddark, alpha)));
        });

        btn_set.setOnClickListener(_view -> startActivityForResult(file, REQ_CD_FILE));
    }

    private void initializeLogic() {
        _palette();
        _ClickAnimation(60, btn_vibrant);
        _ClickAnimation(60, btn_vibrantlight);
        _ClickAnimation(60, btn_vibrantdark);
        _ClickAnimation(60, btn_muted);
        _ClickAnimation(60, btn_mutedlight);
        _ClickAnimation(60, btn_muteddark);
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case REQ_CD_FILE:
                if (_resultCode == Activity.RESULT_OK) {
                    ArrayList<String> _filePath = new ArrayList<>();
                    if (_data != null) {
                        if (_data.getClipData() != null) {
                            for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
                                ClipData.Item _item = _data.getClipData().getItemAt(_index);
                                _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
                            }
                        } else {
                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
                        }
                    }
                    img_color.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get(0), 1024, 1024));
                    _palette();
                } else {

                }
                break;
            default:
                break;
        }
    }

    public void _palette() {
        img_color = findViewById(R.id.img_color);
        Bitmap icon = ((BitmapDrawable) img_color.getDrawable()).getBitmap();
        Palette.from(icon).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int vibrant = palette.getVibrantColor(Color.parseColor("#FFFFFF"));
                btn_vibrant.setBackgroundColor(vibrant);
                bg_color.setBackgroundColor(vibrant);
                int colorRGB = Integer.valueOf(vibrant);
                int r = Color.red(colorRGB);
                int g = Color.green(colorRGB);
                int b = Color.blue(colorRGB);
                double red = r;
                double blue = b;
                double green = g;
                _convertColor(red, green, blue);
                tv_color.setText(colorPicked);
                if ((red > 180) || ((blue > 180) || (green > 180))) {
                    tv_color.setTextColor(0xFF000000);
                } else {
                    tv_color.setTextColor(0xFFFFFFFF);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btn_vibrant.setTooltipText(_getColorView(btn_vibrant, alpha));
                }
                int vibrantLight = palette.getLightVibrantColor(Color.parseColor("#FFFFFF"));
                btn_vibrantlight.setBackgroundColor(vibrantLight);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btn_vibrantlight.setTooltipText(_getColorView(btn_vibrantlight, alpha));
                }
                int vibrantDark = palette.getDarkVibrantColor(Color.parseColor("#FFFFFF"));
                btn_vibrantdark.setBackgroundColor(vibrantDark);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btn_vibrantdark.setTooltipText(_getColorView(btn_vibrantdark, alpha));
                }
                int muted = palette.getMutedColor(Color.parseColor("#FFFFFF"));
                btn_muted.setBackgroundColor(muted);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btn_muted.setTooltipText(_getColorView(btn_muted, alpha));
                }
                int mutedLight = palette.getLightMutedColor(Color.parseColor("#FFFFFF"));
                btn_mutedlight.setBackgroundColor(mutedLight);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btn_mutedlight.setTooltipText(_getColorView(btn_mutedlight, alpha));
                }
                int mutedDark = palette.getDarkMutedColor(Color.parseColor("#FFFFFF"));
                btn_muteddark.setBackgroundColor(mutedDark);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    btn_muteddark.setTooltipText(_getColorView(btn_muteddark, alpha));
                }
            }
        });
    }


    public void _ClickAnimation(final double _animDuration, final View _view) {
        _view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ObjectAnimator scaleX = new ObjectAnimator();
                        scaleX.setTarget(_view);
                        scaleX.setPropertyName("scaleX");
                        scaleX.setFloatValues(0.9f);
                        scaleX.setDuration((int) _animDuration);
                        scaleX.start();

                        ObjectAnimator scaleY = new ObjectAnimator();
                        scaleY.setTarget(_view);
                        scaleY.setPropertyName("scaleY");
                        scaleY.setFloatValues(0.9f);
                        scaleY.setDuration((int) _animDuration);
                        scaleY.start();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        ObjectAnimator scaleX = new ObjectAnimator();
                        scaleX.setTarget(_view);
                        scaleX.setPropertyName("scaleX");
                        scaleX.setFloatValues((float) 1);
                        scaleX.setDuration((int) _animDuration);
                        scaleX.start();

                        ObjectAnimator scaleY = new ObjectAnimator();
                        scaleY.setTarget(_view);
                        scaleY.setPropertyName("scaleY");
                        scaleY.setFloatValues((float) 1);
                        scaleY.setDuration((int) _animDuration);
                        scaleY.start();

                        break;
                    }
                }
                return false;
            }
        });
    }


    public String _getColorView(final View _view, final boolean _alpha) {
        ColorDrawable cd = (ColorDrawable) _view.getBackground();
        if (_alpha) {
            return String.format("#%08X", (0xFFFFFFFF & cd.getColor())).toUpperCase();
        } else {
            return String.format("#%06X", (0xFFFFFF & cd.getColor())).toUpperCase();
        }
    }


    public void _convertColor(final double _r, final double _g, final double _b) {
        colorPicked = "#".concat(String.format("%02X%02X%02X", (int) _r, (int) _g, (int) _b));
    }


    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int[] _location = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
