package com.zd.lbsx;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
/*
 * create by Juice Zhu
 */
import android.view.View.OnClickListener;

public abstract class XActBase extends FragmentActivity implements OnClickListener {

    /**
     * ��ʼ�����ֽ���
     * @return ���ز��ֽ���layout��id
     */
    protected abstract int setContentLayout();

    /**
     * ��ʼ���ؼ�
     */
    protected abstract void initView();
    /**
     *��ʼ��������
     */
    protected abstract void initListener();
    /**
     * ��ʼ������
     */
    protected abstract void initData();
    
    protected Context context;
	
    
    @Override
    protected void onCreate(Bundle arg0) {
    	super.onCreate(arg0);
    	setContentView(setContentLayout());
    	context=this;
        initView();
        initListener();
        initData();
    }

}
