package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BlackJackTableGUI extends Application {

//Creating Controls and Labels for Blackjack GUI
	
	
	Label dealerHand = new Label("Dealer Hand");
	Label dealerValueLbl = new Label("Value: ");
	Label playerHand = new Label("Player Hand");
	Label playerValueLbl = new Label("Value: ");
	Label dealerValue = new Label("0");
	Label playerValue = new Label("0");

	Label playerBust = new Label("");
	Label dealerBust = new Label("");

	Label playerScoreLbl = new Label("Player Score: ");
	Label dealerScoreLbl = new Label("Dealer Score: ");
	Label playerScore = new Label("0");
	Label dealerScore = new Label("0");

	Label winnerOrNo = new Label("");

	Button start = new Button("Start");
	Button hit = new Button("Hit");
	Button stand = new Button("Stand");

	HBox dealerCardBox = new HBox();
	HBox playerCardBox = new HBox();

	HBox dealerHandValue = new HBox(dealerValueLbl, dealerValue, dealerBust);
	HBox dealerTitle = new HBox(100, dealerHand, dealerHandValue);

	HBox playerHandValue = new HBox(playerValueLbl, playerValue, playerBust);
	HBox playerTitle = new HBox(100, playerHand, playerHandValue);

	HBox playerScoreBox = new HBox(playerScoreLbl, playerScore);
	HBox dealerScoreBox = new HBox(dealerScoreLbl, dealerScore);
	VBox playerAndDealerBox = new VBox(playerScoreBox, dealerScoreBox);
	HBox scoreAndWinnerBox = new HBox(280, playerAndDealerBox, winnerOrNo);

	HBox buttons = new HBox(10, start, hit, stand);
	
//Creating a new player and dealer object
	Player player = new Player();
	Player dealer = new Player();

// Incrementing the winner count and hand index
	int cardCount = 0;
	int winnerPlayer = 1;
	int winnerDealer = 1;
	@Override
	public void start(Stage primaryStage) {
		
		

//Setting the GUI
		dealerTitle.setAlignment(Pos.CENTER);
		playerTitle.setAlignment(Pos.CENTER);
		buttons.setAlignment(Pos.CENTER);

		playerCardBox.setPrefSize(600, 150);
		dealerCardBox.setPrefSize(600, 150);

		playerScoreBox.setPrefWidth(150);
		dealerScoreBox.setPrefWidth(150);

		start.setOnAction(new StartHandler());
		hit.setOnAction(new HitHandler());
		stand.setOnAction(new StandHandler());

		GridPane gPane = new GridPane();
		gPane.setPadding(new Insets(10));

		gPane.add(dealerTitle, 0, 0);
		gPane.add(dealerCardBox, 0, 1);
		gPane.add(playerTitle, 0, 2);
		gPane.add(playerCardBox, 0, 3);
		gPane.add(buttons, 0, 4);
		gPane.add(scoreAndWinnerBox, 0, 5);
		
		

		Scene scene = new Scene(gPane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Blackjack");
		primaryStage.show();
		
		
		
	}
//EventHandler for the start button
	class StartHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
//Calling the start game method
			startGame();

		}
	}
//EventHandler for the hit button
	class HitHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

//Calling the update hand method with player parameters
			updateHand(player, playerCardBox, playerValue);

		}
	}

//EventHandler for the stand button
	class StandHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			cardCount = 1;
//Gets the playerValue text
			int otherPlayerValue = Integer.parseInt(playerValue.getText());

			boolean standOrNo = dealer.stand(otherPlayerValue);
			
			while (standOrNo == true) {
//Checks if dealer has busted, won, or equals the player hand
				if (dealer.bust() == false) {

					dealerCardBox.getChildren().add(dealer.hand.get(cardCount));
					dealerValue.setText("" + dealer.valueOfHand());
					cardCount++;

				}

				if (dealer.bust() == true) {

					winnerOrNo.setText("Player wins!");
					dealerBust.setText(" Bust!");
					playerScore.setText("" + winnerPlayer);
					winnerPlayer++;
					standOrNo = false;

					endGame();

				}

				if (dealer.value == 21) {

					winnerOrNo.setText("Dealer wins!");
					dealerScore.setText("" + winnerDealer);
					winnerDealer++;
					standOrNo = false;

					endGame();
				}

				if (dealer.value == player.value) {

					winnerOrNo.setText("Push! No One Wins!");
					standOrNo = false;
					endGame();
				}
			}

		}

	}
	
//Method for when user starts the game
	public void startGame() {
		
// Reseting the deck, hand, and values for dealer and player	
		player.clearHand();
		dealer.clearHand();

		dealerCardBox.getChildren().clear();
		playerCardBox.getChildren().clear();

		dealerValue.setText("0");
		playerValue.setText("0");

		winnerOrNo.setText("");
		playerBust.setText("");
		dealerBust.setText("");
		
//Makes a new player and dealer object
		Player.deck.reset();
		player = new Player();
		dealer = new Player();

//Displaying a new card from the dealer's hand
		dealerCardBox.getChildren().add(dealer.hand.get(cardCount));
		dealerValue.setText("" + dealer.valueOfHand());

//Disabling and enabling buttons
		start.setDisable(true);
		hit.setDisable(false);
		stand.setDisable(false);

	}

//Displaying the card for the player
	public void updateHand(Player p, HBox box, Label handValue) {
		
//Checks if player has busted, won, or equals to 21
		if (player.bust() == false) {

			playerCardBox.getChildren().add(player.hand.get(cardCount));
			playerValue.setText("" + player.valueOfHand());
			cardCount++;
		}

		if (player.bust() == true) {

			winnerOrNo.setText("Dealer wins!");
			playerBust.setText(" Bust!");
			dealerScore.setText("" + winnerDealer);
			winnerDealer++;
			
			endGame();
			
		}
		if (player.value == 21) {

			winnerOrNo.setText("Player wins!");
			playerScore.setText("" + winnerPlayer);
			winnerPlayer++;
			
			endGame();
			
		}
		
	}
	
//Method for when either the player or dealer wins
	public void endGame() {

		cardCount = 0;

		start.setDisable(false);
		hit.setDisable(true);
		stand.setDisable(true);

	}

	public static void main(String[] args) {
		launch(args);
		
		
	}
}
