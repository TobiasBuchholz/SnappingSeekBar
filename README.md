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
<div style="background: #f0f0f0; overflow:auto;width:auto;"><pre style="margin: 0; line-height: 125%"><span style="color: #666666">&lt;</span>com<span style="color: #666666">.</span><span style="color: #4070a0">tobishiba</span><span style="color: #666666">.</span><span style="color: #4070a0">SnappingSeekBarSample</span><span style="color: #666666">.</span><span style="color: #4070a0">views</span><span style="color: #666666">.</span><span style="color: #4070a0">SnappingSeekBar</span>
    <span style="color: #002070; font-weight: bold">android:</span>layout_width<span style="color: #666666">=</span><span style="color: #4070a0">&quot;match_parent&quot;</span>
    <span style="color: #002070; font-weight: bold">android:</span>layout_height<span style="color: #666666">=</span><span style="color: #4070a0">&quot;wrap_content&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>progressDrawable<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@drawable/apptheme_scrubber_progress_horizontal_holo_light&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>thumb<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@drawable/apptheme_scrubber_control_selector_holo_light&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>progressColor<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@color/blue&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>thumbnailColor<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@color/blue_light&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>indicatorColor<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@color/white&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>textIndicatorColor<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@color/white&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>textSize<span style="color: #666666">=</span><span style="color: #4070a0">&quot;12dp&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>indicatorSize<span style="color: #666666">=</span><span style="color: #4070a0">&quot;12dp&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>itemsArrayId<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@array/seek_bar_with_big_indicators_items&quot;</span><span style="color: #666666">/&gt;</span>
</pre></div>
 
</br>
</br>
Most of the attributes have a default value, so the minimum setup looks like the following:
 
<div style="background: #f0f0f0; overflow:auto;width:auto;"><pre style="margin: 0; line-height: 125%"><span style="color: #666666">&lt;</span>com<span style="color: #666666">.</span><span style="color: #4070a0">tobishiba</span><span style="color: #666666">.</span><span style="color: #4070a0">SnappingSeekBarSample</span><span style="color: #666666">.</span><span style="color: #4070a0">views</span><span style="color: #666666">.</span><span style="color: #4070a0">SnappingSeekBar</span>
    <span style="color: #002070; font-weight: bold">android:</span>id<span style="color: #666666">=</span><span style="color: #4070a0">&quot;@+id/activity_main_seek_bar_without_texts&quot;</span>
    <span style="color: #002070; font-weight: bold">android:</span>layout_width<span style="color: #666666">=</span><span style="color: #4070a0">&quot;match_parent&quot;</span>
    <span style="color: #002070; font-weight: bold">android:</span>layout_height<span style="color: #666666">=</span><span style="color: #4070a0">&quot;wrap_content&quot;</span>
    <span style="color: #002070; font-weight: bold">app:</span>itemsAmount<span style="color: #666666">=</span><span style="color: #4070a0">&quot;5&quot;</span><span style="color: #666666">/&gt;</span>
</pre></div>
 
</br>
####From code it could look like this:
<div style="background: #f0f0f0; overflow:auto;width:auto;"><pre style="margin: 0; line-height: 125%"><span style="color: #228899; font-weight: bold">private</span> SnappingSeekBar <span style="color: #6666ff; font-weight: bold">createSnappingSeekBarProgrammatically</span><span style="color: #333333">()</span> <span style="color: #333333">{</span>
    <span style="color: #228899; font-weight: bold">final</span> SnappingSeekBar snappingSeekBar <span style="color: #333333">=</span> <span style="color: #228899; font-weight: bold">new</span> SnappingSeekBar<span style="color: #333333">(</span><span style="color: #228899; font-weight: bold">this</span><span style="color: #333333">);</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setProgressDrawable</span><span style="color: #333333">(</span>R<span style="color: #333333">.</span><span style="color: #000077">drawable</span><span style="color: #333333">.</span><span style="color: #000077">apptheme_scrubber_progress_horizontal_holo_light</span><span style="color: #333333">);</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setThumbDrawable</span><span style="color: #333333">(</span>R<span style="color: #333333">.</span><span style="color: #000077">drawable</span><span style="color: #333333">.</span><span style="color: #000077">apptheme_scrubber_control_selector_holo_light</span><span style="color: #333333">);</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setItems</span><span style="color: #333333">(</span><span style="color: #228899; font-weight: bold">new</span> String<span style="color: #333333">[]{</span><span style="background-color: #e0e0ff">&quot;Wow&quot;</span><span style="color: #333333">,</span> <span style="background-color: #e0e0ff">&quot;such&quot;</span><span style="color: #333333">,</span> <span style="background-color: #e0e0ff">&quot;amazing&quot;</span><span style="color: #333333">});</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setProgressColor</span><span style="color: #333333">(</span>resources<span style="color: #333333">.</span><span style="color: #000077">getColor</span><span style="color: #333333">(</span>R<span style="color: #333333">.</span><span style="color: #000077">color</span><span style="color: #333333">.</span><span style="color: #000077">green_darker</span><span style="color: #333333">));</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setThumbnailColor</span><span style="color: #333333">(</span>resources<span style="color: #333333">.</span><span style="color: #000077">getColor</span><span style="color: #333333">(</span>R<span style="color: #333333">.</span><span style="color: #000077">color</span><span style="color: #333333">.</span><span style="color: #000077">yellow_light</span><span style="color: #333333">));</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setTextIndicatorColor</span><span style="color: #333333">(</span>resources<span style="color: #333333">.</span><span style="color: #000077">getColor</span><span style="color: #333333">(</span>R<span style="color: #333333">.</span><span style="color: #000077">color</span><span style="color: #333333">.</span><span style="color: #000077">red_darker</span><span style="color: #333333">));</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setIndicatorColor</span><span style="color: #333333">(</span>resources<span style="color: #333333">.</span><span style="color: #000077">getColor</span><span style="color: #333333">(</span>R<span style="color: #333333">.</span><span style="color: #000077">color</span><span style="color: #333333">.</span><span style="color: #000077">green_light</span><span style="color: #333333">));</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setTextSize</span><span style="color: #333333">(</span><span style="color: #6666ff; font-weight: bold">14</span><span style="color: #333333">);</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setIndicatorSize</span><span style="color: #333333">(</span><span style="color: #6666ff; font-weight: bold">14</span><span style="color: #333333">);</span>
    snappingSeekBar<span style="color: #333333">.</span><span style="color: #000077">setOnItemSelectionListener</span><span style="color: #333333">(</span><span style="color: #228899; font-weight: bold">this</span><span style="color: #333333">);</span>
    <span style="color: #228899; font-weight: bold">return</span> snappingSeekBar<span style="color: #333333">;</span>
<span style="color: #333333">}</span>
</pre></div>
 </br>
##Contribution
If you would like to contribute to this project make sure you send pull request to <b>dev</b> branch or create an issue.

##Developed by
* Tobias Buchholz - <tobias.buchhokz89@gmail.com>