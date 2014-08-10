SnappingSeekBar
===============
 
This is a sample app which contains my implementation of a snapping seek bar. If you build the project you can see some different seek bar examples which are all attached to the same OnItemSelectionListener.
 
##Demo
You can also download the sample app in the Google Play Store: [demo](https://play.google.com/store/apps/details?id=com.tobishiba.SnappingSeekBarSample)

<img src="http://api.qrserver.com/v1/create-qr-code/?color=000000&amp;bgcolor=FFFFFF&amp;data=https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.tobishiba.SnappingSeekBarSample&amp;qzone=1&amp;margin=0&amp;size=150x150&amp;ecc=L" alt="qr code" />
 
##Features
 - create the seek bar from xml or from layout
 - the progress bar contains indicators and optional texts below those indicators
 - after releasing the thumb it snaps to the nearest indicator with an smooth animation
 - it is very customizable -> change drawables, colors, amount of items etc.
 - set up everything properly and the seek bar does the rest for you
 
##Usage
 
####In xml use the following attributes:


    <com.tobishiba.SnappingSeekBarSample.views.SnappingSeekBar
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
    	app:progressDrawable="@drawable/apptheme_scrubber_progress_horizontal_holo_light"
    	app:thumb="@drawable/apptheme_scrubber_control_selector_holo_light"
    	app:progressColor="@color/blue"
    	app:thumbnailColor="@color/blue_light"
    	app:indicatorColor="@color/white"
    	app:textIndicatorColor="@color/white"
    	app:textSize="12dp"
    	app:indicatorSize="12dp"
    	app:itemsArrayId="@array/seek_bar_with_big_indicators_items"/> 

</br>
</br>
Most of the attributes have a default value, so the minimum setup looks like the following:

	<com.tobishiba.SnappingSeekBarSample.views.SnappingSeekBar
    	android:id="@+id/activity_main_seek_bar_without_texts"
	    android:layout_width="match_parent"
    	android:layout_height="wrap_content"
	    app:itemsAmount="5"/>

</br>
####From code it could look like this:
	private SnappingSeekBar createSnappingSeekBarProgrammatically() {
    	final SnappingSeekBar snappingSeekBar = new SnappingSeekBar(this);
		snappingSeekBar.setProgressDrawable(R.drawable.apptheme_scrubber_progress_horizontal_holo_light);
	    snappingSeekBar.setThumbDrawable(R.drawable.apptheme_scrubber_control_selector_holo_light);
    	snappingSeekBar.setItems(new String[]{"Wow", "such", "amazing"});
	    snappingSeekBar.setProgressColor(resources.getColor(R.color.green_darker));
    	snappingSeekBar.setThumbnailColor(resources.getColor(R.color.yellow_light));
    	snappingSeekBar.setTextIndicatorColor(resources.getColor(R.color.red_darker));
    	snappingSeekBar.setIndicatorColor(resources.getColor(R.color.green_light));
    	snappingSeekBar.setTextSize(14);
    	snappingSeekBar.setIndicatorSize(14);
    	snappingSeekBar.setOnItemSelectionListener(this);
    	return snappingSeekBar;
	}


 </br>
##Contribution
If you would like to contribute to this project make sure you send pull request to <b>dev</b> branch or create an issue.

##Developed by
* Tobias Buchholz - <tobias.buchholz89@gmail.com>
