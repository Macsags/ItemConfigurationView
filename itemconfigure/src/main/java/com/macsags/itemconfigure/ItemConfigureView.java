package com.macsags.itemconfigure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;


public class ItemConfigureView extends RelativeLayout {
    /*根布局*/
    private RelativeLayout mRootLayout;
    /*分割线*/
    private View mUnderLine;
    /*整体根布局view*/
    private View mView;

    /*左侧图标*/
    private Drawable mLeftIcon;
    /*左侧图标控件*/
    private ImageView mIvLeftIcon;
    /*左侧显示文本*/
    private String mLeftText;
    /*左侧文本控件*/
    private TextView mTvLeftText;
    /*左侧显示文本大小*/
    private int mTextSize;
    /*左侧显示文本颜色*/
    private int mTextColor;
    /*左侧文本第二级符号*/
    private TextView tv_left_text_level1;
    /*左侧单选项*/
    private RadioButton mRadioBtn;
    /*左侧图标展示风格*/
    private int mLeftStyle = 0;

    /*右侧显示文本*/
    private String mRightText;
    /*右侧显示hint文本*/
    private String mRightHintText;
    /*右侧Icon显示文本*/
    private String mRightIconText;
    /*右侧图标*/
    private Drawable mRightIcon;
    /*右侧文本控件*/
    private EditText mEdtRightText;
    /*右侧图标控件区域,默认展示图标*/
    private RelativeLayout mRightLayout;
    /*右侧图标控件,默认不展示图标*/
    private TextView mTvRightIcon;
    /*右侧图标控件,选择样式图标*/
    private AppCompatCheckBox mCbRightIcon;
    /*右侧图标控件,开关样式图标*/
    private SwitchCompat mSwitchRightIcon;
    /*右侧图标展示风格*/
    private int mRightStyle = 0;
    boolean isRightEdtNumber = false;

    /*点击事件*/
    private OnSettingItemClick mOnSettingItemClick;
    /*check监听*/
    private OnCheckChangedListener mOnCheckChangedListener;
    /*文字改变相加监听事件*/
    private OnSettingAddTextChangedListener mOnSettingAddTextChangedListener;

    public ItemConfigureView(Context context) {
        this(context, null);
    }

    public ItemConfigureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemConfigureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getCustomStyle(context, attrs);
        //获取到右侧展示风格，进行样式切换
        switchRightStyle(mRightStyle);
        //获取到左侧展示风格，进行样式切换
        switchLeftStyle(mLeftStyle);
    }

    public void setOnSettingItemClick(OnSettingItemClick mOnSettingItemClick) {
        this.mOnSettingItemClick = mOnSettingItemClick;
    }

    public void setOnSettingAddTextChangedListener(OnSettingAddTextChangedListener mOnSettingAddTextChangedListener) {
        this.mOnSettingAddTextChangedListener = mOnSettingAddTextChangedListener;
    }

    public void setOnCheckChangedListener(OnCheckChangedListener mOnCheckChangedListener) {
        this.mOnCheckChangedListener = mOnCheckChangedListener;
    }


    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    public void getCustomStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemConfigurationView);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.ItemConfigurationView_leftText) {
                mLeftText = a.getString(attr);
                mTvLeftText.setText(mLeftText);
            } else if (attr == R.styleable.ItemConfigurationView_leftIcon) {
                // 左侧图标
                mIvLeftIcon.setVisibility(VISIBLE);
                mLeftIcon = a.getDrawable(attr);
                mIvLeftIcon.setImageDrawable(mLeftIcon);
            } else if (attr == R.styleable.ItemConfigurationView_textSize) {
                // 默认设置为14sp，TypeValue也可以把sp转化为px
                mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
                mTvLeftText.setTextSize(mTextSize);
            } else if (attr == R.styleable.ItemConfigurationView_textColor) {
                //文字默认灰色
                mTextColor = a.getColor(attr, Color.LTGRAY);
                mTvLeftText.setTextColor(mTextColor);
            } else if (attr == R.styleable.ItemConfigurationView_rightStyle) {
                mRightStyle = a.getInt(attr, 0);
            } else if (attr == R.styleable.ItemConfigurationView_leftStyle) {
                mLeftStyle = a.getInt(attr, 0);
            } else if (attr == R.styleable.ItemConfigurationView_isShowUnderLine) {
                //默认显示分割线
                if (!a.getBoolean(attr, true)) {
                    mUnderLine.setVisibility(View.GONE);
                }
            } else if (attr == R.styleable.ItemConfigurationView_clickable) {
                //默认可点击
                if (!a.getBoolean(attr, true)) {
                    mRootLayout.setBackgroundResource(R.color.white);
                    mRootLayout.setClickable(false);
                    mRootLayout.setFocusable(false);
                }
            } else if (attr == R.styleable.ItemConfigurationView_editTextClickable) {
                //默认可点击
                if (!a.getBoolean(attr, true)) {
                    mEdtRightText.setClickable(false);
                    mEdtRightText.setFocusable(false);
                }
            } else if (attr == R.styleable.ItemConfigurationView_rightText) {
                mRightText = a.getString(attr);
                mEdtRightText.setText(mRightText);
            } else if (attr == R.styleable.ItemConfigurationView_rightIconText) {
                mRightIconText = a.getString(attr);
                mTvRightIcon.setText(mRightIconText);
            } else if (attr == R.styleable.ItemConfigurationView_backgroundWith) {
                mRootLayout.setBackground(a.getDrawable(attr));
            } else if (attr == R.styleable.ItemConfigurationView_hintText) {
                SpannableString s = new SpannableString(a.getString(attr));
                mEdtRightText.setHint(s);
            } else if (attr == R.styleable.ItemConfigurationView_radioButtonName) {
                mRadioBtn.setText(a.getString(attr));
            } else if (attr == R.styleable.ItemConfigurationView_edtNumber) {
                isRightEdtNumber = a.getBoolean(attr, true);
            }
        }
        a.recycle();
    }

    /**
     * 根据设定切换右侧展示样式，同时更新点击事件处理方式
     *
     * @param mRightStyle
     */
    public void switchRightStyle(int mRightStyle) {
        this.mRightStyle = mRightStyle;//重新赋值，不然外部传递过来的mRightStyle会有问题
        switch (mRightStyle) {
            case 0:
                //默认什么都没有
                mTvRightIcon.setVisibility(View.GONE);
                mCbRightIcon.setVisibility(View.GONE);
                mSwitchRightIcon.setVisibility(View.GONE);
                break;
            case 1:
                //展示样式，只展示一个图标
                mCbRightIcon.setVisibility(View.GONE);
                mSwitchRightIcon.setVisibility(View.GONE);
                mTvRightIcon.setVisibility(VISIBLE);
                mTvRightIcon.setText("");//隐藏单位
                mTvRightIcon.setBackgroundResource(R.drawable.chevron_thin_right);
                setEditTextOnTouch();
                break;
            case 2:
                //显示选择框样式
                mTvRightIcon.setVisibility(View.GONE);
                mCbRightIcon.setVisibility(View.VISIBLE);
                mSwitchRightIcon.setVisibility(View.GONE);
                setEditTextOnTouch();
                break;
            case 3:
                //显示开关切换样式
                mTvRightIcon.setVisibility(View.GONE);
                mCbRightIcon.setVisibility(View.GONE);
                mSwitchRightIcon.setVisibility(View.VISIBLE);
                setEditTextOnTouch();
                break;
            case 4:
                //隐藏右侧图标显示单位Text
                mCbRightIcon.setVisibility(View.GONE);
                mSwitchRightIcon.setVisibility(View.GONE);
                mTvRightIcon.setVisibility(View.VISIBLE);
                //带单位的默认数字键盘
                mEdtRightText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                mEdtRightText.setFilters(new InputFilter[]{
                        new InputFilter() {
                            public CharSequence filter(CharSequence src, int start,
                                                       int end, Spanned dst, int dstart, int dend) {

                                if (src.equals("")) // for backspace
                                    return src;
                                if (src.toString().matches("[0-9.]*")) //put your constraints here
                                    return src;

                                return "";
                            }
                        }
                });
                break;
        }
    }

    public void switchLeftStyle(int mLeftStyle) {
        switch (mLeftStyle) {
            case 0:
                //默认展示样式
                mRadioBtn.setVisibility(View.GONE);
                tv_left_text_level1.setVisibility(View.GONE);
                break;
            case 1:
                //展示radioButton
                mRadioBtn.setVisibility(View.VISIBLE);
                break;
            case 2:
                mIvLeftIcon.setVisibility(View.VISIBLE);
                mIvLeftIcon.setImageDrawable(getResources().getDrawable((R.drawable.l1sign)));
                break;
            case 3:
                tv_left_text_level1.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView(Context context) {
        mView = View.inflate(context, R.layout.item_configure, this);
        mRadioBtn = mView.findViewById(R.id.radioBtn);
        tv_left_text_level1 = mView.findViewById(R.id.tv_left_text_level1);
        mRootLayout = mView.findViewById(R.id.rootLayout);
        mUnderLine = mView.findViewById(R.id.underline);
        mTvLeftText = mView.findViewById(R.id.tv_left_text);
        mIvLeftIcon = mView.findViewById(R.id.iv_left_icon);
        mTvRightIcon = mView.findViewById(R.id.tv_right_icon);
        mEdtRightText = mView.findViewById(R.id.edt_right_text);
        mRightLayout = mView.findViewById(R.id.fl_right_layout);
        mCbRightIcon = mView.findViewById(R.id.right_check);
        mSwitchRightIcon = mView.findViewById(R.id.right_switch);

        mRootLayout.setOnClickListener(v -> {
            clickOn();
            if (null != mOnCheckChangedListener) {
                mOnCheckChangedListener.checkChange(mCbRightIcon.isChecked());
            }
        });

        mCbRightIcon.setOnClickListener(view -> {
            if (null != mOnCheckChangedListener) {
                mOnCheckChangedListener.checkChange(mCbRightIcon.isChecked());
            }
        });

//        mEdtRightText.setOnClickListener(view -> {
//            if (null != mOnSettingItemClick) {
//                mOnSettingItemClick.click();
////                mRootLayout.performClick();
//            }
//        });

        //点击edit，光标默认在最后
        mEdtRightText.setOnFocusChangeListener((v, hasFocus) -> new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEdtRightText.setSelection(mEdtRightText.getText().toString().length());
            }
        }, 10));

//        mEdtRightText.addTextChangedListener(new MoneyTextWatcher(mEdtRightText) {
//            /*  private String outStr = ""; //这个值存储输入超过两位数时候显示的内容
//
//              @Override
//              public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                  if(!TextUtils.isEmpty(mRightIconText)){
//                      String edit=s.toString();
//                      if(!edit.contains(".")){
//                          if (edit.length()==2&&Integer.parseInt(edit)>=10){
//                              outStr=edit;
//                          }
//                      }
//                  }
//              }
//
//              @Override
//              public void onTextChanged(CharSequence s, int start, int before, int count) {
//                  if(!TextUtils.isEmpty(mRightIconText)){
//                      if(mRightIconText.equals("%")){
//                          String words = s.toString();
//                          //首先内容进行非空判断，空内容（""和null）不处理
//                          if (!TextUtils.isEmpty(words)) {
//                              if(words.contains(".")){
//                                  words = words.substring(0,words.indexOf("."));
//                                  mEdtRightText.setText(words);
//                                  mEdtRightText.setSelection(words.length());
//                              }
//
//                              if(words.length() > 1){
//                                  if (words.startsWith("0")) {
//                                      words=words.substring(1);
//                                      mEdtRightText.setText(words);
//                                      mEdtRightText.setSelection(words.length());
//                                  }
//                              }
//                              //1-100的正则验证
//                              Pattern p = Pattern.compile("^(100|[1-9]\\d|\\d)$");
//                              Matcher m = p.matcher(words);
//                              if (m.find() || ("").equals(words)) {
//                                  //这个时候输入的是合法范围内的值
//                              } else {
//                                  if (words.length() > 2) {
//                                      //若输入不合规，且长度超过2位，继续输入只显示之前存储的outStr
//                                      mEdtRightText.setText(outStr);
//                                      //重置输入框内容后默认光标位置会回到索引0的地方，要改变光标位置
//                                      mEdtRightText.setSelection(2);
//                                  }
//                                  ToastUtil.showShortToast("请输入范围在0-100之间的整数");
//                              }
//                          }
//                      }
//                  }
//
//              }*/
//            @Override
//            public void afterTextChanged(Editable s) {
////                Log.e("TAG", "afterTextChanged: ");
//                super.afterTextChanged(s);
//                string = s.toString();
//                if (mOnSettingAddTextChangedListener != null) {
//                    mOnSettingAddTextChangedListener.textChange(s.toString());
//                }
//
//                if (isRightEdtNumber) {
////                    if (TextUtils.isEmpty(s.toString())) {
////                        mEdtRightText.setText("0");
////                        mEdtRightText.setSelection(1);
////                    }
//                    if (s.toString().startsWith("0")) {
//                        mEdtRightText.setSelection(1);
//                    }
//                    if (s.toString().length() >= 2 && s.toString().startsWith("0")) {
//                        mEdtRightText.setText(s.toString().substring(1));
//                        mEdtRightText.setSelection(s.toString().length() - 1);
//                    }
//                }
//            }
//
//        });
    }

    private void clickOn() {
        switch (mRightStyle) {
            case 2:
                //选择框切换选中状态
                mCbRightIcon.setChecked(!mCbRightIcon.isChecked());
                break;
            case 3:
                //开关切换状态
                mSwitchRightIcon.setChecked(!mSwitchRightIcon.isChecked());
                break;
        }
        if (null != mOnSettingItemClick) {
            mOnSettingItemClick.click();
        }
    }

    public interface OnSettingItemClick {
        void click();
    }

    public interface OnSettingAddTextChangedListener {
        void textChange(String string);
    }

    public interface OnCheckChangedListener {
        void checkChange(Boolean isChecked);
    }


    /**
     * 设置点击事件是否生效
     */
    public void setIsClickOK(boolean isOk) {
        mRootLayout.setClickable(isOk);
        mRootLayout.setFocusable(isOk);
    }

    /**
     * 设置点击事件是否生效
     */
    public void setEditClickable(boolean isOk) {
        mEdtRightText.setClickable(isOk);
        mEdtRightText.setFocusable(isOk);
        mRootLayout.setClickable(isOk);
        mRootLayout.setFocusable(isOk);
    }

    /**
     * 右侧输入框是否为空并给出提示
     */
    public String RightIsEmpty() {
        if (TextUtils.isEmpty(mEdtRightText.getText().toString())) {
            return "请输入" + mTvLeftText.getText().toString();
        } else {
            return "";
        }
    }


    /**
     * 设置TvRightIcon文字
     */
    public void setTvRightIcon(String pS) {
        mTvRightIcon.setText(pS);
    }

    /**
     * 设置RightCheckIsCheck
     */
    public void setRightCheckIsCheck(boolean pB) {
        mCbRightIcon.setChecked(pB);
    }


    /**
     * 设置EditText监听关联根布局
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setEditTextOnTouch() {
        mEdtRightText.setClickable(false);
        mEdtRightText.setFocusable(false);
        mEdtRightText.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                mRootLayout.setPressed(true);//按下触发根布局press
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mRootLayout.setPressed(false);//抬起触发根布局press
                mRootLayout.performClick();//反射根布局点击事件
            }
            return false;
        });
    }

    /**
     * set底布局背景
     */
    public void setBootLayoutBackground(int id) {
        if (mRootLayout != null)
            mRootLayout.setBackgroundResource(id);
    }

    /**
     * set左侧ImageView图标
     */
    public void setLeftIcon(int id) {
        if (mTvLeftText != null)
            mIvLeftIcon.setImageDrawable(getResources().getDrawable((id)));
    }

    /**
     * set左侧TextView文字
     */
    public void setLeftText(String t) {
        if (mTvLeftText != null)
            mTvLeftText.setText(t);
    }

    /**
     * get左侧TextView文字
     */
    public CharSequence getLeftText() {
        if (mTvLeftText != null)
            return mTvLeftText.getText();
        else
            return null;
    }

    /**
     * append左侧TextView文字
     */
    public void appendLeftText(String t) {
        if (mTvLeftText != null)
            mTvLeftText.append(t);
    }

    /**
     * set左侧TextView文字大小
     */
    public void setLeftTextSize(float size) {
        if (mTvLeftText != null)
            mTvLeftText.setTextSize(size);
    }

    /**
     * set左侧TextView文字颜色
     */
    public void setLeftTextColor(int color) {
        if (mTvLeftText != null)
            mTvLeftText.setTextColor(color);
    }

    /**
     * set左侧TextView背景颜色
     */
    public void setLeftTextBackgroundColor(int color) {
        if (mTvLeftText != null)
            mTvLeftText.setBackgroundColor(color);
    }

    /**
     * set左侧TextView左、上、右、下的内边距
     */
    public void setLeftTextPadding(int left, int top, int right, int bottom) {
        if (mTvLeftText != null)
            mTvLeftText.setPadding(left, top, right, bottom);
    }

    /**
     * set右侧EditText是否可编辑
     */
    public void setEditTextEnabled(boolean enabled) {
        if (mEdtRightText != null)
            mEdtRightText.setEnabled(enabled);
    }

    /**
     * set右侧EditText InputType 属性
     * InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
     */
    public void setEditTextInputType(int type) {
        if (mEdtRightText != null)
            mEdtRightText.setInputType(type);
    }

    /**
     * set右侧EditText输入框显隐
     */
    public void setEditTextVisibility(int visibility) {
        if (mEdtRightText != null)
            mEdtRightText.setVisibility(visibility);
    }

    /**
     * get右侧EditText输入框内容
     */
    public String getEditText() {
        if (mEdtRightText != null)
            return mEdtRightText.getText().toString();
        else
            return "";

    }

    /**
     * set右侧EditText输入框文字
     */
    public void setEditText(String text) {
        if (mEdtRightText != null)
            mEdtRightText.setText(text);
    }

    /**
     * set右侧EditText光标位置
     */
    public void setEditTextSelection(int index) {
        if (mEdtRightText != null)
            mEdtRightText.setSelection(index);
    }

    /**
     * set右侧EditText光标位置
     */
    public void setEditTextSelection(int start, int stop) {
        if (mEdtRightText != null)
            mEdtRightText.setSelection(start, stop);

    }

    /**
     * set右侧EditText光标颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void setEditTextCursorDrawable(int textCursorDrawable) {
        if (mEdtRightText != null)
            mEdtRightText.setTextCursorDrawable(textCursorDrawable);
    }

    /**
     * set右侧EditText光标颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void setEditTextCursorDrawable(@Nullable Drawable textCursorDrawable) {
        if (mEdtRightText != null)
            mEdtRightText.setTextCursorDrawable(textCursorDrawable);
    }

    /**
     * 右侧输入框hint ,默认是填写+左侧text组成
     */
    public void setEditTextHint(CharSequence hint) {
        if (mEdtRightText != null) {
            if (TextUtils.isEmpty(hint))
                mEdtRightText.setHint("填写" + mTvLeftText.getText().toString());
            else
                mEdtRightText.setHint(hint);
        }
    }

    /**
     * 只允许输入整形
     * 0 默认是只输入数字
     * 1 身份证
     * 2 手机号
     */
    public void setEditTextIsNumber(int isType) {
        isRightEdtNumber = true;

        switch (isType) {
            case 0:
                mEdtRightText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
                break;
            case 1:
                mEdtRightText.setKeyListener(DigitsKeyListener.getInstance("1234567890xX"));
                mEdtRightText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
                break;
            case 2:
                mEdtRightText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                break;
            default:
                break;
        }
    }

    /**
     * 右侧输入框限制可输入的字母数字（String）， max为最大可输入数字数，0为默认不改动;
     */
    public void setEditTextKeyListenerAndFilters(@NonNull String accepted, int max) {
        if (mEdtRightText != null) {
            if (!TextUtils.isEmpty(accepted)) {
                mEdtRightText.setKeyListener(DigitsKeyListener.getInstance(accepted));
                return;
            }
            if (max != 0) {
                mEdtRightText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
            }

        }
    }

    /**
     * set右侧textView 文字（可理解为改单位）
     */
    public void setRightIconText(String txt) {
        if (mTvRightIcon != null)
            mTvRightIcon.setText(txt);
    }

    /**
     * get右侧textView 文字（可理解为改单位）
     */
    public CharSequence getRightIconText() {
        if (mTvRightIcon != null) {
            return mTvRightIcon.getText();
        } else {
            return null;
        }
    }

    /**
     * set底布局背景
     */
    public void setRightIconTextBackground(int id) {
        if (mTvRightIcon != null)
            mTvRightIcon.setBackgroundResource(id);
    }

    /**
     * set右侧checkbox是否选中
     */
    public void setCbRightIconChecked(Boolean checked) {
        mCbRightIcon.setChecked(checked);
    }
    /**
     * get右侧checkbox是否选中
     */
    public boolean getCbRightIconIsChecked() {
        return mCbRightIcon.isChecked();
    }
    /**
     * set右侧checkbox是否选中
     */
    public void setSwitchRightIconChecked(Boolean checked) {
        mSwitchRightIcon.setChecked(checked);
    }
    /**
     * get右侧checkbox是否选中
     */
    public boolean getSwitchRightIconIsChecked() {
        return mSwitchRightIcon.isChecked();
    }


}

