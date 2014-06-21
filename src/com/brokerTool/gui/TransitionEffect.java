package com.brokerTool.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TransitionEffect {

	public static void makeTransition(Pane pane) {

		try {
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(
					new KeyFrame(Duration.ZERO, new KeyValue(pane
							.opacityProperty(), 0)));

			for (int i = 1; i < 10; i++) {
				timeline.getKeyFrames().add(
						new KeyFrame(new Duration(i * 200), new KeyValue(pane
								.opacityProperty(), i / 10.0)));
			}

			timeline.play();
		} catch (Exception ex) {

		}

	}

}
