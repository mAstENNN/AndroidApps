package com.example.project4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {
    volatile boolean isGameRunning = true;
    int gopherPosition;
    final int GOPHER = 0, PLAYER1 = 1, PLAYER2 = 2;
    final int SUCCESS = 1, NEAR_MISS = 2, CLOSE_GUESS = 3, COMPLETE_MISS = 4;

    int[] holeOccupancy = new int[100]; ///tracks the status of each position on the grid
    boolean isGopherFound = false;
    TextView p1Status, p2Status, gameStatus;
    Button stopButton;
    final String[] outcomes = {"", "Success", "Near Miss", "Close Guess", "Complete Miss"};
    private String[] gridData1 = new String[100]; //store the game data for player 1
    private String[] gridData2 = new String[100]; //store the game data for player 12
    private volatile boolean isPlayer1Turn = true;  //flag to check if it's player 1's turn
    Button switchPlayerButton;
    boolean isPlayerOneActive = true;
    GridView gridView1, gridView2; //adapter to populate the grid views
    TextView player1Label, player2Label; //handlers for managing player actions
    GridAdapter gridAdapter1; //adapter to populate the grid views
    GridAdapter gridAdapter2;//adapter to populate the grid views
    private Handler mHandler; //handlers for managing player actions
    private Handler player1Handler; //handlers for managing player1 actions
    private Handler player2Handler; //handlers for managing player2 actions
    Player1Runnable player1Runnable; //threads for player1 actions
    Player2Runnable player2Runnable; //threads for player2 actions
    Thread player1Thread;
    Thread player2Thread;



//    private final BroadcastReceiver stopGameReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("com.project4.ACTION_STOP_GAME".equals(intent.getAction())) {
//                isGameRunning = false;
//                player1Runnable.stop();
//                player2Runnable.stop();
//            }
//        }
//    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //initial setup of grid views and buttons
        gridView1 = findViewById(R.id.gridView1);
        gridView2 = findViewById(R.id.gridView2);
        Arrays.fill(gridData1, "");
        Arrays.fill(gridData2, "");
        Arrays.fill(holeOccupancy, -1);

        //randomly assign gopher position
        gopherPosition = (int) (Math.random() * 100);
        gridData1[gopherPosition] = "G";
        gridData2[gopherPosition] = "G";
        holeOccupancy[gopherPosition] = GOPHER;

        //setup grid adapters
        gridAdapter1 = new GridAdapter(this, gridData1);
        gridAdapter2 = new GridAdapter(this, gridData2);
        GridView gridView1 = (GridView) findViewById(R.id.gridView1);
        GridView gridView2 = (GridView) findViewById(R.id.gridView2);
        gridView2.setAdapter(gridAdapter2);
        gridView1.setAdapter(gridAdapter1);

        //setup switch player button
        switchPlayerButton = (Button) findViewById(R.id.switchPlayerButton);
        switchPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayer();
            }
        });

        //setup game status labels and buttons
        player1Label = findViewById(R.id.player1Label);
        player2Label = findViewById(R.id.player2Label);

        p1Status = findViewById(R.id.player1_move);
        p2Status = findViewById(R.id.player2_move);

        gameStatus = findViewById(R.id.gameStatus);
        stopButton = findViewById(R.id.stop_game);

        stopButton.setOnClickListener(v -> {
            isGameRunning = false;  //set a flag to stop the thread
            player1Thread.interrupt();
            player2Thread.interrupt();
            stopButton.setEnabled(false);
        });

        mHandler = new Handler(Looper.getMainLooper());

        player1Runnable = new Player1Runnable();
        player2Runnable = new Player2Runnable();
        player1Thread = new Thread(player1Runnable);
        player2Thread = new Thread(player2Runnable);
        player1Thread.start();
        player2Thread.start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            player1Handler = player1Runnable.handler;
            player2Handler = player2Runnable.handler;
        }, 1000);

    }


    //method to place an item in the grid based on the player
    public void placeItem(int position, boolean isPlayerOne, String item) {
        if (isPlayerOne) {
            gridData1[position] = item; //update player 1's grid
            gridAdapter1.notifyDataSetChanged(); //notify adapter for data change
        } else {
            gridData2[position] = item; //update player 2's grid
            gridAdapter2.notifyDataSetChanged();//notify adapter for data change
        }
    }

    //calculate proximity of a move to the gopher position
    public int calcGopherProximity(int pos) {
        int row = pos / 10, col = pos % 10;
        int gopherRow = gopherPosition / 10, gopherCol = gopherPosition % 10;

        if (pos == gopherPosition) {return SUCCESS;} //check if position matches gopher position
        else if (Math.abs(row - gopherRow) <= 1 && Math.abs(col - gopherCol) <= 1) {return NEAR_MISS;} //check for near miss
        else if (Math.abs(row - gopherRow) <= 2 && Math.abs(col - gopherCol) <= 2) {return CLOSE_GUESS;} //check for close guess
        else return COMPLETE_MISS;  //return complete miss if none of the above
    }


    class Player1Runnable implements Runnable {
        private Handler handler;

        public void run() {
            Looper.prepare(); //set up the looper for this thread.

            handler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0 && isGameRunning && !isGopherFound && isPlayer1Turn) {
                        int moveResult = makeMove(PLAYER1); //execute player 1 move
                        mHandler.post(() -> updateUIBasedOnOutcome(PLAYER1, moveResult)); //post task to update UI on main thread
                        isPlayer1Turn = false; //change turn to player 2
                        //schedule the next move for Player 2 after 2 seconds
                        handler.postDelayed(() -> {
                            if (isGameRunning && !isGopherFound) {
                                player2Handler.sendEmptyMessage(0);
                            }
                        }, 2000);
                    }
                }
            };

            //player 1 starts first and this trigger the first move
            handler.sendEmptyMessage(0);
            Looper.loop(); //start the loop
        }
    }

    class Player2Runnable implements Runnable {
        private Handler handler;

        public void run() {
            Looper.prepare(); //set up the looper for this thread.

            handler = new Handler(Looper.myLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0 && isGameRunning && !isGopherFound && !isPlayer1Turn) {
                        int moveResult = makeMove(PLAYER2); //execute player 2 move
                        mHandler.post(() -> updateUIBasedOnOutcome(PLAYER2, moveResult));
                        isPlayer1Turn = true;  //change turn to player 1
                        //schedule the next move for Player 1 after 2 seconds
                        handler.postDelayed(() -> {
                            if (isGameRunning && !isGopherFound) {
                                player1Handler.sendEmptyMessage(0);
                            }
                        }, 2000);
                    }
                }
            };

            Looper.loop(); //start the loop
        }
    }



    private int moveCount1 = 0; //counter for moves made by Player 1
    private int moveCount2 = 0;//counter for moves made by Player 12
    private int makeMove(int playerType) {
        int pos = findUnoccupiedPosition();  //get a random unoccupied position.
        if (pos != -1) {
            int outcome = calcGopherProximity(pos); //calculate how close the move is to the gopher

            if (playerType == PLAYER1) {
                moveCount1++;
                mHandler.post(() -> placeItem(pos, true, String.valueOf(moveCount1))); // Place Player 1 move on the grid
            } else {
                moveCount2++;
                mHandler.post(() -> placeItem(pos, false, String.valueOf(moveCount2))); // Place Player 2 move on the grid
            }

            if (outcome == SUCCESS) {
                isGopherFound = true; //set flag if gopher is found
            }
            return outcome;
        }
        return COMPLETE_MISS;
    }

    private void updateUIBasedOnOutcome(int playerType, int outcome) {
        runOnUiThread(() -> {
            TextView statusView = (playerType == PLAYER1) ? p1Status : p2Status;
            statusView.setText(outcomes[outcome]); //set text to display the outcome of the move
            if (outcome == SUCCESS) {
                gameStatus.setText("Player " + (playerType == PLAYER1 ? "1" : "2") + " WON");
            }
        });
    }



    private int findUnoccupiedPosition() {
        int position;
        do {
            position = (int) (Math.random() * 100); //randomly select a position
        } while (holeOccupancy[position] == PLAYER1 || holeOccupancy[position] == PLAYER2); //ensure the position is not already occupied by any player
        return position;
    }

    //toggle the active player view
    private void togglePlayer() {
        runOnUiThread(() -> {
            isPlayerOneActive = !isPlayerOneActive;

            if (isPlayerOneActive) {
                gridView1.setVisibility(View.VISIBLE);
                gridView2.setVisibility(View.GONE);

                player1Label.setVisibility(View.VISIBLE);
                player2Label.setVisibility(View.GONE);

                p1Status.setVisibility(View.VISIBLE);
                p2Status.setVisibility(View.GONE);
            } else {
                gridView1.setVisibility(View.GONE);
                gridView2.setVisibility(View.VISIBLE);

                player1Label.setVisibility(View.GONE);
                player2Label.setVisibility(View.VISIBLE);

                p1Status.setVisibility(View.GONE);
                p2Status.setVisibility(View.VISIBLE);
            }
        });
    }

}

