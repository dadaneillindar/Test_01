package tw.edu.tp.cksh.test_01;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadAsyncTask_Now extends AsyncTask<URL, Void, Bitmap> {
	
	public MainActivity_NowPosition refnow;

	@Override
	protected Bitmap doInBackground(URL... urls) {
		if (urls.length <= 0)
			throw new java.lang.ArrayIndexOutOfBoundsException();

		try {
			InputStream stream = urls[0].openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(stream);
			stream.close();
			return bitmap;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		ImageView title = ((ImageView) refnow.findViewById(R.id.imageView1));
		if (result == null) {
			// "無法連網QAQ
		} else {
			title.setImageBitmap(result);
		}
	}
}