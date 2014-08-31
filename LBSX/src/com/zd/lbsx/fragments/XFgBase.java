package com.zd.lbsx.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class XFgBase extends Fragment {

	private Context mContext;
	private LayoutInflater mInflater;
    /**
     * ��ʼ�����ֽ���
     * @return ���ز��ֽ���layout��id
     */
    protected abstract int setFragmentView();

    /**
     * ͨ��v.findViewById()��ʼ���ؼ�
     * @param v
     */
    protected abstract void initView(View v);

    /**
     * ��ʼ��������
     */
    protected abstract void initListener();

    /**
     * ���ݰ�,���������ʼ��
     */
    protected abstract void initData();
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	mContext=getActivity();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	this.mInflater=inflater;
    	View view=inflater.inflate(setFragmentView(), null);
        initView(view);
        initListener();
        initData();
    	return view;
    }
	
}
