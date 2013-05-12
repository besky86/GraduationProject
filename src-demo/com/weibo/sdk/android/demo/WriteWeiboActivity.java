package com.weibo.sdk.android.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.DialogListener;
import com.weibo.sdk.android.api.FriendshipsAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.demo.HomeActivity.UserRequestListener;
import com.weibo.sdk.android.entity.Status;
import com.weibo.sdk.android.entity.User;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.util.StringUtil;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class WriteWeiboActivity extends Activity implements DialogListener {

	private File filePic;

	private Button btn_send;
	private ImageView inserted_iv;
	private Button btn_clear;
	private EditText et_content;
	private ImageButton btn_insert_topic;
	private ImageButton btn_insert_at;
	private ImageButton btn_insert_pic;
	private ArrayList<String> friendsNameList = new ArrayList<String>();
	private ArrayList<CharSequence> friendsSNameList = new ArrayList<CharSequence>();
	private ArrayList<String> topicList = new ArrayList<String>();
	CharSequence[] friendsItems;
	private Button btn_back;

	private String picPath;// 文件路径
	private static final int PHOTO_WITH_CAMERA = 1010;// 拍摄照片
	private static final int PHOTO_WITH_DATA = 1020;// 从SD中得到照片
	private static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/DCIM/Camera");// 拍摄照片存储的文件夹路劲
	private File capturefile;// 拍摄的照片文件

	Handler h = new Handler() {

		public void handleMessage(Message msg) {
			List<User> users = (ArrayList<User>) User.getUsersList(msg
					.getData().getString("response"));
			for (User user : users) {
				friendsNameList.add(user.getScreen_name());
				Log.v("users", user.getIdstr());
			}

			CharSequence[] friendsItems = new CharSequence[friendsNameList
					.size()];
			int index = 0;
			while (index < friendsNameList.size()) {
				friendsItems[index] = friendsNameList.get(index);
				Log.v("friends", friendsItems[index].toString());
				index++;
			}

			addListeners();

			// getUsers(statusList);

		}
	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_write_weibo);

		getViews();

		addListeners();
		


	}

	private void getViews() {

		btn_insert_pic = (ImageButton) findViewById(R.id.btn_tool_camera);
		btn_send = (Button) findViewById(R.id.btn_send);
		inserted_iv = (ImageView) findViewById(R.id.add_image_iv);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_insert_at = (ImageButton) findViewById(R.id.btn_tool_at);
		btn_insert_topic = (ImageButton) findViewById(R.id.btn_tool_topic);
		btn_back = (Button) findViewById(R.id.btn_back);
		FriendshipsAPI friendShipAPI = new FriendshipsAPI(
				MainTabActivity.accessToken);
		friendShipAPI.friends(
				Long.parseLong(MainTabActivity.accessToken.getUid()), 50, 0,
				false, new FriendsRequestListenerLisener());

	}
	private void addListeners() {
		
		btn_back.setOnClickListener(new OnClickListener( ){

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				WriteWeiboActivity.this.finish();
			}
			
		});
		
		
		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				String content = et_content.getText().toString();
				StatusesAPI statusAPI = new StatusesAPI(
						MainTabActivity.accessToken);
				if (filePic != null) {

					statusAPI.upload(content, filePic.getAbsoluteFile()
							.toString(), "0.0", "0.0",
							new UpdateStatusRequestListener());
				}
				else
					statusAPI.update(content, "0.0", "0.0",
							new UpdateStatusRequestListener());

			}

		});

		// 给编辑框添加文本内容改变监听器
		et_content.addTextChangedListener(new TextWatcher() {

			private CharSequence temp;
			@Override
			public void afterTextChanged(Editable s) {

				// TODO Auto-generated method stub
				int length = 0;
				for (int index = 0; index < temp.length(); index++) {
					char tempChar = temp.charAt(index);

					// 判断中文字符
					if (// (tempChar > 0x00 && tempChar < 0xff)||
					(tempChar > '\u4E00' && tempChar < '\u9FA5')) {
						length += 2;
					}
					else
						length++;
				}
				WriteWeiboActivity.this.btn_clear.setText("" + (280 - length)
						/ 2);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// TODO Auto-generated method stub
				temp = s;

			}

		});

		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				WriteWeiboActivity.this.et_content.setText("");

			}

		});

		btn_insert_at.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				int start = et_content.getSelectionStart();
				Log.v("StartIn11", "" + start);
				Editable editable = et_content.getText();
				et_content.setText(editable.insert(start, "@"));

				editable = et_content.getText();
				Selection.setSelection(editable, ++start);

				Log.v("StartIn", "" + start);

				// 创建builder
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						WriteWeiboActivity.this);

				builder.setTitle("好友列表")
						// 标题
						.setIcon(R.drawable.btn_insert_at_nor)
						// icon
						.setItems(
								friendsItems,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Editable editable = et_content
												.getText();

										int start = et_content
												.getSelectionStart();
										// Editable editable = et_content
										// .getText();
										editable.insert(start,
												friendsItems[which] + " ");

										start += (friendsItems[which].length() + 1);

										et_content.setText(StringUtil
												.getSpannableString(et_content
														.getText().toString()));

										editable = et_content.getText();
										Log.v("StartIn", "" + start);
										// 将光标设置在所@用户之后
										Selection.setSelection(editable, start);
									}

								});
				// 创建Dialog对象
				AlertDialog dlg = builder.create();
				dlg.show();
			}

		});

		btn_insert_topic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				int start = et_content.getSelectionStart();
				Log.v("StartIn11", "" + start);
				Editable editable = et_content.getText();
				et_content.setText(editable.insert(start, "##"));

				editable = et_content.getText();
				Selection.setSelection(editable, ++start);

				Log.v("StartIn", "" + start);

				final CharSequence[] items = {"Item1", "Item2", "Item3"};

				// 创建builder
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						WriteWeiboActivity.this);

				builder.setTitle("话题选择列表")
						// 标题
						.setIcon(R.drawable.btn_insert_topic_nor)
						// icon
						.setItems(
								items,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Editable editable = et_content
												.getText();

										int start = et_content
												.getSelectionStart();
										// Editable editable = et_content
										// .getText();
										editable.insert(start, items[which]);

										start += (items[which].length() + 1);

										et_content.setText(StringUtil
												.getSpannableString(et_content
														.getText().toString()));

										editable = et_content.getText();
										Log.v("StartIn", "" + start);
										// 将光标设置在所@用户之后
										Selection.setSelection(editable, start);
									}

								});
				// 创建Dialog对象
				AlertDialog dlg = builder.create();
				dlg.show();
			}

		});

		btn_insert_pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				final CharSequence[] choices = {"相机拍摄", "本地相册"};
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						WriteWeiboActivity.this);

				builder.setTitle("设置")
						// 标题
						.setIcon(R.drawable.btn_insert_pic_nor)
						// icon
						.setItems(
								choices,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										switch (which) {
											case 0 : {
												String status = Environment
														.getExternalStorageState();
												if (status
														.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
													Intent i = new Intent(
															MediaStore.ACTION_IMAGE_CAPTURE);
													capturefile = new File(
															PHOTO_DIR,
															getPhotoFileName());
													try {
														capturefile
																.createNewFile();
														i.putExtra(
																MediaStore.EXTRA_OUTPUT,
																Uri.fromFile(capturefile));// 将拍摄的照片信息存到capturefile中
													}
													catch (IOException e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
													}

													startActivityForResult(i,
															PHOTO_WITH_CAMERA);// 用户点击了从照相机获取
												}
												else {
													Util.showToast(
															WriteWeiboActivity.this,
															"没有SD卡");
												}
												break;

											}
											case 1 :// 从相册中去获取
												Intent intent = new Intent();
												/* 开启Pictures画面Type设定为image */
												intent.setType("image/*");
												/*
												 * 使用Intent.
												 * ACTION_GET_CONTENT这个Action
												 */
												intent.setAction(Intent.ACTION_GET_CONTENT);
												/* 取得相片后返回本画面 */
												startActivityForResult(intent,
														PHOTO_WITH_DATA);
												break;
										}

									}
								});
				// 创建Dialog对象
				AlertDialog dlg = builder.create();
				dlg.show();

			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_weibo, menu);
		return true;
	}

	class UpdateStatusRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {

			// TODO Auto-generated method stub
			Util.showToast(WriteWeiboActivity.this, "发表动态成功");
			WriteWeiboActivity.this.finish();

			Log.v("Update", response);

		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(WriteWeiboActivity.this, "发表动态异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(WriteWeiboActivity.this, "发表动态失败");
		}

	}

	@Override
	public void onComplete(String response) {

		// TODO Auto-generated method stub
		et_content.setText(et_content.getText().toString() + response);

	}

	class FriendsRequestListenerLisener implements RequestListener {
		@Override
		public void onComplete(String response) {

			// Delete by Lei@2013/05/07 DEL START
			// List<User> users = (ArrayList<User>) User.getUsersList(response);
			// for (User user : users) {
			// friendsNameList.add(user.getScreen_name());
			// }
			// Delete by Lei@2013/05/07 DEL END
			// TODO Auto-generated method stub
			// Delete by Lei@2013/05/07 DEL START
			Log.v("users", response);
			Message msg = new Message();
			msg.getData().putString("response", response);
			WriteWeiboActivity.this.h.sendMessage(msg);
			// Delete by Lei@2013/05/07 DEL END

		}

		@Override
		public void onIOException(IOException e) {

			// TODO Auto-generated method stub

		}

		@Override
		public void onError(WeiboException e) {

			// TODO Auto-generated method stub

		}

	}

	String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		File file = null;
		Bitmap pic = null;
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case PHOTO_WITH_CAMERA :// 获取拍摄的文件
					picPath = capturefile.getAbsolutePath();
					System.out.println(picPath);
					file = new File(picPath);
					pic = decodeFile(file);
					// WriteWeiboActivity.this.filePic =file;
					inserted_iv.setImageBitmap(pic);
					System.out.println("++++++相机+++++");
					break;

				case PHOTO_WITH_DATA :// 获取从图库选择的文件
					Uri uri = data.getData();
					String scheme = uri.getScheme();
					if (scheme.equalsIgnoreCase("file")) {
						picPath = uri.getPath();
						System.out.println(picPath);
						file = new File(picPath);
						pic = decodeFile(file);
						// WriteWeiboActivity.this.filePic = file;
						inserted_iv.setImageBitmap(pic);
					}
					else
						if (scheme.equalsIgnoreCase("content")) {
							Cursor cursor = getContentResolver().query(uri,
									null, null, null, null);
							cursor.moveToFirst();
							picPath = cursor.getString(1);
							file = new File(picPath);
							// WriteWeiboActivity.this.filePic= file;
							pic = decodeFile(file);
							inserted_iv.setImageBitmap(pic);

						}
					break;
			}

			inserted_iv.setVisibility(View.VISIBLE);
			WriteWeiboActivity.this.filePic = file;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/*
	 * 压缩图片，避免内存不足报错
	 */
	Bitmap decodeFile(File f) {
		Bitmap b = null;
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;

			FileInputStream fis = new FileInputStream(f);
			BitmapFactory.decodeStream(fis, null, o);
			fis.close();

			int scale = 1;
			if (o.outHeight > 100 || o.outWidth > 100) {
				scale = (int) Math.pow(
						2,
						(int) Math.round(Math.log(100 / (double) Math.max(
								o.outHeight, o.outWidth)) / Math.log(0.5)));
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			fis = new FileInputStream(f);
			b = BitmapFactory.decodeStream(fis, null, o2);
			fis.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

}
