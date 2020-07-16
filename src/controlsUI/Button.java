//Defines button size and style for all UI buttons
package controlsUI;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Button extends javafx.scene.control.Button {
	static Background btnHover = new Background(new BackgroundFill(Color.LIMEGREEN, new CornerRadii(10), new Insets(0)));
	static Background btnNormal = new Background(new BackgroundFill(Color.FORESTGREEN, new CornerRadii(10), new Insets(0)));
	static Background btnClicked = new Background(new BackgroundFill(Color.SADDLEBROWN, new CornerRadii(10), new Insets(0)));
	static DropShadow shad = new DropShadow();
	public static int SCREEN_HEIGHT;

	public Button(String Cont) {
		super(Cont);
		setFont(Font.font(SCREEN_HEIGHT * 0.02100000000000));
		setMaxWidth(SCREEN_HEIGHT * 0.15);
		setMinWidth(SCREEN_HEIGHT * 0.15);
		setMaxHeight(SCREEN_HEIGHT * 4.8 / 100);
		setMinHeight(SCREEN_HEIGHT * 4.8 / 100);
		setBackground(btnNormal);
		setOnMouseEntered(e -> {
			setBackground(btnHover);
			setEffect(shad);
		});
		setOnMouseExited(e -> {
			if (!isPressed()) {
				setBackground(btnNormal);
				setTextFill(Color.BLACK);
				setEffect(null);
			}
		});
		setOnMousePressed(e -> {
			setBackground(btnClicked);
			setTextFill(Color.WHITE);
			setEffect(shad);
		});
		setOnMouseReleased(e -> {
			if (isHover()) {
				setBackground(btnHover);
				setTextFill(Color.BLACK);
				setEffect(shad);
			} else {
				setBackground(btnNormal);
				setTextFill(Color.BLACK);
				setEffect(null);
			}
		});
		setCursor(Cursor.HAND);
	}

	public static void initBtn(int s) {
		SCREEN_HEIGHT = s;
		shad.setHeight(30);
		shad.setWidth(30);
		shad.setSpread(.3);
	}
}
