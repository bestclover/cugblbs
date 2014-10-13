package com.zd.lbsx.fragments;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zd.lbsx.R;
import com.zd.util.UploadUtil;

public class XFgFind1 extends XFgBase implements OnClickListener{
	 private static final String TAG = "uploadImage";
	    private static String requestURL = "http://geekzhu.xicp.net/XgMy/upload";
	    private Button selectImage,uploadImage;
	    private ImageView imageView;
	    
	    public String picPath = null;
	    
	    int progressStatus = 0;//��������ǰ״̬
	    ProgressDialog pd;//������
	    
	    Handler handler=new Handler(){
	    	public void handleMessage(Message msg) {
	    		if(msg.what == 1){
	    			if(msg.arg1 >= 100){
	    				pd.dismiss();
	    				Toast.makeText(getActivity(), "�ϴ��ɹ�", Toast.LENGTH_SHORT).show();
	    			}else{
	    				pd.setProgress(msg.arg1);
	    			}
	    		}
	    	};
	    };
	    
	    
	    
	    
	    @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	        case R.id.selectImage:
	            /***
	             * ����ǵ���android���õ�intent��������ͼƬ�ļ�   ��ͬʱҲ���Թ���������  
	             */
	            Intent intent = new Intent();
//	            intent.setType("image/*");
	            intent.setType("application/doc");
	            intent.setAction(Intent.ACTION_GET_CONTENT);       
	            startActivityForResult(intent, 1);
	            break;
	        case R.id.uploadImage:
//	        	Log.i("<><><><>", picPath);
	        	if(picPath == null){
	        		Log.i("<<<<<","picPath�ǿյ�");
	        		Toast.makeText(getActivity(), "����ѡ���ĵ�~", Toast.LENGTH_SHORT).show();
	        		return;
	        	}
	            final File file = new File(picPath);
	            if(file!=null)
	            {
	            	//��ʾ������
	            	showProgress(getActivity());
	            	
	            	new Thread(new Runnable() {

						@Override
						public void run() {
							String request = null;
							
							request = UploadUtil.uploadFile( file, requestURL, handler);
							
							if(request != null){
								Message msg = new Message();
								msg.what = 0;
								handler.sendMessage(msg);
							}
						}
					}).start();
	            	
	            }
	            break;
	        default:
	            break;
	        }
	    }

//	    
	    @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if(resultCode==Activity.RESULT_OK)
	        {
	            /**
	             * ��ѡ���ͼƬ��Ϊ�յĻ����ڻ�ȡ��ͼƬ��;��  
	             */
	            Uri uri = data.getData();
	            Log.e(TAG, "uri = "+ uri); 
	            
	            
	            String path = uri.toString();
	            if(path.endsWith("doc")||path.endsWith("txt")||path.endsWith("pdf"))
                {
                    //��ʾ�ı���Ϣ
	            	String filePath = path.substring(7);
	            	Log.i("filePath", filePath);
	            	picPath = filePath;
	            	alertSuccess();
	            	
	            	Log.i("picPath", picPath);
                }else{
                	alert();
                }
	            
	            
	            
//	            try {
////	            	String[] pojo = {MediaStore.Images.Media.DATA};
//	                String[] pojo = {MediaStore.Images.Media.DATA};
//	                
//	                Cursor cursor = getActivity().managedQuery(uri, pojo, null, null,null);
//	                if(cursor!=null)
//	                {
//	                    ContentResolver cr = getActivity().getContentResolver();
//	                    int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//	                    cursor.moveToFirst();
//	                    String path = cursor.getString(colunm_index);
//	                    /***
//	                     * ���������һ���ж���Ҫ��Ϊ�˵����������ѡ�񣬱��磺ʹ�õ��������ļ��������Ļ�����ѡ����ļ��Ͳ�һ����ͼƬ�ˣ������Ļ��������ж��ļ��ĺ�׺��
//	                     * �����ͼƬ��ʽ�Ļ�����ô�ſ���   
//	                     */
////	                    if(path.endsWith("jpg")||path.endsWith("png"))
//	                    if(path.endsWith("doc")||path.endsWith("txt")||path.endsWith("pdf"))
//	                    {
//	                        picPath = path;
////	                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
////	                        imageView.setImageBitmap(bitmap);
//	                        Log.i("<<<<",picPath);
//	                    }else{alert();}
//	                }else{alert();}
//	                
//	            } catch (Exception e) {
//	            }
	        }
	        
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	    
	    private void alertSuccess()
	    {
	        Dialog dialog = new AlertDialog.Builder(getActivity())
	        .setTitle("��ʾ")
//	        .setMessage("��ѡ��Ĳ�����Ч��ͼƬ")
	        .setMessage("ѡ��ɹ�")
	        .setPositiveButton("ȷ��",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog,
	                            int which) {
	                    }
	                })
	        .create();
	        
	        dialog.show();
	    }
	    
	    private void alert()
	    {
	        Dialog dialog = new AlertDialog.Builder(getActivity())
	        .setTitle("��ʾ")
//	        .setMessage("��ѡ��Ĳ�����Ч��ͼƬ")
	        .setMessage("��ѡ��Ĳ�����Ч���ı��ļ�")
	        .setPositiveButton("ȷ��",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog,
	                            int which) {
	                        picPath = null;
	                    }
	                })
	        .create();
	        
	        dialog.show();
	    }

		@Override
		protected int setFragmentView() {
			// TODO Auto-generated method stub
			return R.layout.fg_find1;
		}

		@Override
		protected void initView(View v) {
			// TODO Auto-generated method stub
			selectImage = (Button) v.findViewById(R.id.selectImage);
	        uploadImage = (Button) v.findViewById(R.id.uploadImage);
	        
	        
	        imageView = (ImageView) v.findViewById(R.id.imageView);
		}

		@Override
		protected void initListener() {
			// TODO Auto-generated method stub
			selectImage.setOnClickListener(this);
	        uploadImage.setOnClickListener(this);
		}

		@Override
		protected void initData() {
			// TODO Auto-generated method stub
			
		}
		
		public void showProgress (Activity activity){
			//������������ɽ�������Ϊ0
			pd = new ProgressDialog(activity);
			pd.setMax((int) com.zd.util.ProgressDialog.MAX_PROGRESS);
			//���öԻ������
			pd.setTitle("�����ϴ�");
			//���öԻ�����ʾ������
			pd.setMessage("����");
			//���öԻ�������ȡ����ť�ر�
			pd.setCancelable(false);
			//���öԻ���Ľ��ȷ��
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			//���öԻ���Ľ������Ƿ���ʾ����
			pd.setIndeterminate(false);
			pd.show();
		}
}
