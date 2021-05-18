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



        front_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.frontanimator
        ) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.backanimation
        ) as AnimatorSet

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
        }



        var Front = listOf<String>()

        var Back = listOf<String>()

        var totalNumOfCards:Int = 0
        var currentFlashcard:Int = 0

        var currentScore:Int = 0

        mGroupViewModel.getFlashcards(subGroupName) // this gets the flashcards of the subgroup passed from the intent

        mGroupViewModel.returnedFlashCards.observe(this, { Flashcard ->
            if (Flashcard.isNotEmpty()) { // if there are items in the flashcard set
                shuffle(Flashcard) // randomize the cards

                //set the front and the back of the flashcards
                flashcardFront.text = Flashcard[0].front
                flashcardBack.text = Flashcard[0].back

                totalNumOfCards = Flashcard.size //set the size of the flashcards
                totalCardNum.setText(totalNumOfCards.toString()) //store the size in the textview

                currentFlashcard = 0 // this is the index for the flashcard in the list
                currentCardNumber.setText((currentFlashcard + 1).toString()) // this is the number of the flashcard in the set

                currentScoreNum.setText(currentScore.toString()) // set the initial score
                totalScoreNum.setText(totalNumOfCards.toString()) // set the total possible score

                Flashcard?.forEach { // store the flashcards in the front and the back arrays
                    Front += (it.front)
                    Back += (it.back)
                }
            }
        })









        val correctButton = findViewById<ImageButton>(R.id.CorrectButton)
        val incorrectButton = findViewById<ImageButton>(R.id.IncorrectButton)
        val restartButton = findViewById<Button>(R.id.Restart)

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