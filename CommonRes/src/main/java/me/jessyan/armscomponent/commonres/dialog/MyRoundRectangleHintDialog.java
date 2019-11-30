package me.jessyan.armscomponent.commonres.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jess.arms.utils.AppManagerUtil;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.armscomponent.commonres.R;

/**
 * =============================================
 * 描    述：圆角按钮对话框
 * =============================================
 */
public class MyRoundRectangleHintDialog extends Dialog {
    private TextView tvContent, btnClose, btnSubmit;
    private View viewLine;
    private OnDialogListener listener;

    public MyRoundRectangleHintDialog(Context context) {
        super(context == null ? AppManagerUtil.getCurrentActivity() : context, R.style.MyHintDialog);
        initView("", "", "", "");
    }

    public MyRoundRectangleHintDialog(Context context, String title, String content, String leftValue, String rigthValue) {
        super(context == null ? AppManagerUtil.getCurrentActivity() : context, R.style.MyHintDialog);
        initView(title, content, leftValue, rigthValue);

    }

    private void initView(String title, String content, String leftValue, String rigthValue) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.base_dialog_my_round_rectangle_hint_dialog, null);
        tvContent = contentView.findViewById(R.id.tv_hint);
        btnClose = contentView.findViewById(R.id.tv_cancel);
        btnSubmit = contentView.findViewById(R.id.tv_ok);
        viewLine = contentView.findViewById(R.id.view_line);
        tvContent.setText(ArmsUtils.isEmpty(content) ? "是否确认" : content);
        btnClose.setText(ArmsUtils.isEmpty(leftValue) ? "取消" : leftValue);
        btnSubmit.setText(ArmsUtils.isEmpty(rigthValue) ? "确认" : rigthValue);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onItemViewLeftListener();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onItemViewRightListener();
                }
            }
        });
        setContentView(contentView);
    }

    public void setVisibileBottomView(boolean isVisibileLeft) {
        viewLine.setVisibility(View.GONE);
        btnClose.setVisibility(isVisibileLeft ? View.VISIBLE : View.GONE);
        btnSubmit.setVisibility(isVisibileLeft ? View.GONE : View.VISIBLE);
    }

    public void setTextContent(String value) {
        tvContent.setText(value);
    }

    public void setBtnClose(String value) {
        btnClose.setText(value);
    }

    public void setBtnSubmit(String value) {
        btnSubmit.setText(value);
    }

    public void setOnDialogListener(OnDialogListener listener) {
        this.listener = listener;
    }

    public abstract static class OnDialogListener {
        public void onItemViewLeftListener() {
        }

        public abstract void onItemViewRightListener();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.horizontalMargin = 0;
        getWindow().setAttributes(layoutParams);
    }
}
