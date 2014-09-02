package com.melko.igoogleapp.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.melko.igoogleapp.R;
import com.melko.igoogleapp.adapters.MoviesGridAdapter;
import com.melko.igoogleapp.models.Movie;

public class MoviesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.movies_fragment, container, false);

		GridView moviesGrid = (GridView) view
				.findViewById(R.id.fragment_movie_gv);

		ArrayList<Movie> mTest = new ArrayList<Movie>();
		Movie movie = new Movie();
		movie.setVideo_url("http://igoogleapps.com/apps-video/182-Sector.4.Extraction.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setName("Sector 4 Extraction 2014");
		movie.setIcon_url("http://igoogleapps.com/apps-img/182-Sector%204%20Extraction%202014.jpg");
		movie.setDescription("An elite band of mercenaries is caught behind enemy lines and left for dead. When their mission leader escapes war torn Sector 4, he pledges to return, leaving No One Left Behind.");
		mTest.add(movie);

		movie = new Movie();
		movie.setVideo_url("http://igoogleapps.com/apps-video/181-Think.Like.a.Man.Too.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setName("Think like a Man Too 2014");
		movie.setIcon_url("http://igoogleapps.com/apps-img/181-Think%20like%20a%20Man%20Too%202014.jpg");
		movie.setDescription("All the couples are back for a wedding in Las Vegas, but plans for a romantic weekend go awry when their various misadventures get them into some compromising situations that threaten to derail the big event.");
		mTest.add(movie);

		movie = new Movie();
		movie.setName("The Fault in our stars 2014");
		movie.setVideo_url("http://igoogleapps.com/apps-video/180-The.Fault.in.Our.Stars.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setIcon_url("http://igoogleapps.com/apps-img/180-The%20Fault%20in%20our%20stars%202014.jpg");
		movie.setDescription("Hazel and Gus are two teenagers who share an acerbic wit, a disdain for the conventional, and a love that sweeps them on a journey. Their relationship is all the more miraculous given that Hazel's other constant companion is an oxygen tank, Gus jokes about his prosthetic leg, and they met and fell in love at a cancer support group.");
		mTest.add(movie);

		movie = new Movie();
		movie.setName("Ragamuffin 2014");
		movie.setVideo_url("http://igoogleapps.com/apps-video/176-Ragamuffin.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setIcon_url("http://igoogleapps.com/apps-img/176-Ragamuffin%202014.jpg");
		movie.setDescription("Based on the life of Rich Mullins, a musical prodigy who rose to Christian music fame and fortune only to walk away and live on a Navajo reservation. An artistic genius, raised on a tree");
		mTest.add(movie);

		movie = new Movie();
		movie.setName("Moms Night Out 2014");
		movie.setVideo_url("http://igoogleapps.com/apps-video/179-Moms.Night.Out.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setIcon_url("http://igoogleapps.com/apps-img/179-Moms%20Night%20Out%202014.jpg");
		movie.setDescription("All Allyson and her friends want is a peaceful, grown-up evening of dinner and fun - a long-needed moms' night out. But in order to enjoy high heels, adult conversation, and food not served...");
		mTest.add(movie);

		movie = new Movie();
		movie.setName("Neighbors 2014");
		movie.setVideo_url("http://igoogleapps.com/apps-video/178-Neighbors.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setIcon_url("http://igoogleapps.com/apps-img/178-Neighbors%202014.jpg");
		movie.setDescription("A couple with a newborn baby face unexpected difficulties after they are forced to live next to a fraternity house.");
		mTest.add(movie);

		movie = new Movie();
		movie.setName("April Rain 2014");
		movie.setVideo_url("http://igoogleapps.com/apps-video/175-April.Rain.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setIcon_url("http://igoogleapps.com/apps-img/175-April%20Rain%202014.jpg");
		movie.setDescription("When an eclectic group of terrorists plot to attack the United States from within, it's up to a quasi-military special investigative unit to identify, infiltrate and neutralize the threat.");
		mTest.add(movie);

		movie = new Movie();
		movie.setName("Bad Johnson 2014");
		movie.setVideo_url("http://igoogleapps.com/apps-video/174-Bad.Johnson.2014.720p.BluRay.x264.YIFY.mp4");
		movie.setIcon_url("http://igoogleapps.com/apps-img/174-Bad%20Johnson%202014.jpg");
		movie.setDescription("A charismatic womanizer receives his comeuppance after his penis mysteriously leaves his body and takes human form.");
		mTest.add(movie);

		moviesGrid.setAdapter(new MoviesGridAdapter(getActivity(), mTest));
		return view;
	}
}
