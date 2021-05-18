package com.example.final_app

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.final_app.dataGroup.GroupViewModel
import kotlinx.android.synthetic.main.activity_study.*
import java.util.Collections.shuffle


class study : AppCompatActivity() {

    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true
//    designating the animation objects from /res/animator file

    private lateinit var mGroupViewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)

        //get the viewmodel
        mGroupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        //get the intent which should hold the group and subgroup names
        val intent = intent.extras
        var groupName:String = ""
        var subGroupName:String = ""
        if (intent != null){
            groupName = intent.getString("group_name").toString()
            subGroupName = intent.getString("sub_group_name").toString()
        }

        val scale = applicationContext.resources.displayMetrics.density
        flashcardFront.cameraDistance = 8000 * scale
        flashcardBack.cameraDistance = 8000 * scale
//      using the animation objects to allow cameraDistance to skew text


        front_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.frontanimator
        ) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.backanimation
        ) as AnimatorSet
        // loading the Animator and Inflator with the animation objects

        flipbutton.setOnClickListener {
            if (isFront) {
                front_anim.setTarget(flashcardFront)
                back_anim.setTarget(flashcardBack)
                front_anim.start()
                back_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(flashcardBack)
                back_anim.setTarget(flashcardFront)
                back_anim.start()
                front_anim.start()
                isFront = true
            }
            // the setOnClickListener that actually initiates the animation
        }



        var Front = listOf<String>()

        var Back = listOf<String>()

        var totalNumOfCards:Int = 0
        var currentFlashcard:Int = 0

        var currentScore:Int = 0
        //these are the variables used for the Study Activity Screen

        mGroupViewModel.getFlashcards(subGroupName) // this gets the flashcards of the subgroup passed from the intent

        mGroupViewModel.returnedFlashCards.observe(this, { Flashcard ->
<<<<<<< Updated upstream
            if (Flashcard.isNotEmpty()) { // if there are items in the flashcard set
                shuffle(Flashcard) // randomize the cards
=======
            if (Flashcard.isNotEmpty()) {
                shuffle(Flashcard)
                // shuffle call to shuffle the order of the flashcards

>>>>>>> Stashed changes

                //set the front and the back of the flashcards
                flashcardFront.text = Flashcard[0].front
                flashcardBack.text = Flashcard[0].back
                //setting the text to the flashcard front/back

<<<<<<< Updated upstream
                totalNumOfCards = Flashcard.size //set the size of the flashcards
                totalCardNum.setText(totalNumOfCards.toString()) //store the size in the textview

                currentFlashcard = 0 // this is the index for the flashcard in the list
                currentCardNumber.setText((currentFlashcard + 1).toString()) // this is the number of the flashcard in the set
=======
                totalNumOfCards = Flashcard.size
                totalCardNum.setText(totalNumOfCards.toString())
                //int used to display the number of flashcards in the set

                currentFlashcard = 0
                currentCardNumber.setText((currentFlashcard + 1).toString())
                // current number of flashcards reviewed
>>>>>>> Stashed changes

                currentScoreNum.setText(currentScore.toString()) // set the initial score
                totalScoreNum.setText(totalNumOfCards.toString()) // set the total possible score

                Flashcard?.forEach { // store the flashcards in the front and the back arrays
                    Front += (it.front)
                    Back += (it.back)
                }
            }
        })








        // button used for the check mark to indicate a correct answer
        val correctButton = findViewById<ImageButton>(R.id.CorrectButton)
        // button used for the x to indicate an incorrect answer
        val incorrectButton = findViewById<ImageButton>(R.id.IncorrectButton)
        // Button used to restart the activity
        val restartButton = findViewById<Button>(R.id.Restart)
        // if the correct button is clicked it checks if there are cards in the set then adds
        // to the score on the bottom of the screen and displays a toast message if there are no more cards.
        correctButton.setOnClickListener {
            if (Front.isNotEmpty()) {


                if (currentFlashcard + 1 < totalNumOfCards) {
                    currentScore++
                    currentScoreNum.setText((currentScore).toString())
                    currentFlashcard += 1
                    currentCardNumber.setText((currentFlashcard + 1).toString())
                    flashcardFront.text = Front[currentFlashcard]
                    flashcardBack.text = Back[currentFlashcard]
                }
                else if (currentFlashcard+1 == totalNumOfCards) {
                    currentFlashcard += 1
                    currentScore++
                    currentScoreNum.setText((currentScore).toString())
                    Toast.makeText(
                        this,
                        "You finished this set, press the restart button to study again",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else
                {
                    currentFlashcard += 1
                    Toast.makeText(
                        this,
                        "There are no more flashcards to study, press the restart button",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        //if the incorrect button is clicked it checks if there are cards in the set then adds
        // to the card number on the top of the screen and displays a toast message if there are no more cards.
        incorrectButton.setOnClickListener{
            if (Front.isNotEmpty()) {


                if (currentFlashcard + 1 < totalNumOfCards) {

                    currentFlashcard += 1
                    currentCardNumber.setText((currentFlashcard + 1).toString())
                    flashcardFront.text = Front[currentFlashcard]
                    flashcardBack.text = Back[currentFlashcard]
                }
                else if (currentFlashcard+1 == totalNumOfCards) {
                    currentFlashcard += 1
                    Toast.makeText(
                        this,
                        "You finished this set, press the restart button",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else
                {
                    currentFlashcard += 1
                    Toast.makeText(
                        this,
                        "There are no more flashcards to study, press the restart button",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        restartButton.setOnClickListener { // this restarts the activity when they finish studying and want to go again
            val intent = getIntent()
            finish()
            startActivity(intent) // by restarting the app, the randomize gets called again
        }

        val Backbtn = findViewById<Button>(R.id.Backbutton)
        //the back button stops this activity and goes to the home page
        Backbtn.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}