package com.example.a5dicegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class GamePage : AppCompatActivity() {
    private var winningPoints = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_page)
        supportActionBar?.hide()


        var playerDice = arrayOf(0, 0, 0, 0, 0)
        var comDice = arrayOf(0, 0, 0, 0, 0)
        var textView = findViewById<TextView>(R.id.textView4)
        var textView2 = findViewById<TextView>(R.id.textView9)
        var count = 0
        var count2 = 0
        var tieN = false
        var playerScore = 0
        var comScore = 0
        var change = 0

        var roll = 0
        var c = 0
        var rerolls = 0

        playerDice()
        comDice()

        //getting pass mark through Settings page.kt just to set the winning score

        intent?.let {
            val value = it.getIntExtra("MY_INT_VALUE", winningPoints)
            winningPoints = value
        }

        findViewById<Button>(R.id.troll).setOnClickListener {
            //set player dice
            if (tieN) {
                var x = 0
                var y = 0
                while (x < 5) {
                    var randomNo = (1..6).random()
                    playerDice[x] = randomNo
                    x++
                    diceRoll(x, count)
                }
                while (y < 5) {
                    var randomNo = (1..6).random()
                    comDice[y] = randomNo
                    y++
                    comStrategy(randomNo, y)
                }
                tieN = false
            } else if (count < 5) {
                while (count < 5) {
                    var randomNo = (1..6).random()
                    playerDice[count] = randomNo
                    count++
                    diceRoll(randomNo, count)
                    roll = cal(count, roll)
                }
                while (count2 < 5) {
                    var randomNo = (1..6).random()
                    comDice[count2] = randomNo
                    count2++
                    comStrategy(randomNo, count2)
                }

            } else if (rerolls == 1) {
                var randomNo = (1..6).random()
                check(false)
                c = 0
                for (k in 0..4) {
                    if (playerDice[k] == 0) {
                        c = k
                        break
                    }
                }
                playerDice[c] = randomNo
                c += 1
                diceRoll(randomNo, c)
                rerolls = set(playerDice, rerolls)
                if (roll == 3 && rerolls == 0) {
                    for (i in playerDice) {
                        playerScore += i
                    }
                    textView.text = playerScore.toString() //add players score
                    comDice = computer(comDice)
                    for (i in comDice) {
                        comScore += i
                    }
                    textView2.text = comScore.toString()
                    roll = 0
                    count = 0
                    count2 = 0
                }
            }
            if (playerScore >= winningPoints || comScore >= winningPoints) {
                if (playerScore == comScore) {
                    //tie strategy
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("YOU and COMPUTER achieved the same score")
                    builder.setMessage("“You and the computer have one last throw ”")
                    builder.setPositiveButton("OK", null)
                    val dialog = builder.create()
                    dialog.show()
                    findViewById<Button>(R.id.roll).isEnabled = false
                    tieN = true
                } else {
                    win(playerScore, winningPoints)
                }
            }
        }

        findViewById<Button>(R.id.roll).setOnClickListener {
            if (roll == 0) {
                Toast.makeText(this, "First throw your dices", Toast.LENGTH_SHORT).show()
            } else if (roll < 3) {
                check(true)
                Toast.makeText(this, "Select the dice want to roll", Toast.LENGTH_SHORT).show()
                reroll(playerDice)
                rerolls = 1
                roll += 1
            } else {
                Toast.makeText(
                    this,
                    "you performs the maximum of 3 rolls for that turn",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        findViewById<Button>(R.id.score).setOnClickListener {
            if (change == 0) {
                comDice = computer(comDice)
                for (i in comDice) {
                    comScore += i
                }
                textView2.text = comScore.toString()
                roll = 0
                count = 0
                count2 = 0
                //random strategy and add score
                for (i in playerDice) {
                    playerScore += i
                }
                textView.text = playerScore.toString() //add players score
            }
            if (playerScore >= winningPoints || comScore >= winningPoints) {
                if (playerScore == comScore) {
                    //tie strategy
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("YOU and COMPUTER achieved the same score")
                    builder.setMessage("“You and the computer have one last throw ”")
                    builder.setPositiveButton("OK", null)
                    val dialog = builder.create()
                    dialog.show()
                    findViewById<Button>(R.id.roll).isEnabled = false
                    tieN = true
                } else {
                    win(playerScore, winningPoints)
                }
            }
        }
    }

    fun computer(array: Array<Int>): Array<Int> {
        var noOfRolls = (1..3).random()
        var x = 0
        if (noOfRolls > 1) {
            Toast.makeText(this, "Computer use reroll", Toast.LENGTH_SHORT).show()
            while (noOfRolls == 1) {
                while (x > 5) {
                    var rand = (0..1).random()
                    var randno = (1..6).random()
                    if (rand == 0) {
                        array[x] = randno
                        comStrategy(randno, x)
                    }
                    x += 1
                }
                noOfRolls -= 1
            }
l
        }
        Toast.makeText(this, "Computer is not use reroll", Toast.LENGTH_SHORT).show()
        return array
    }

    fun set(array: Array<Int>, rerolls: Int): Int {
        var x = 0
        var s = rerolls
        for (i in 0..4) {
            if (array[i] != 0) {
                x += 1
            }
        }
        if (x == 5) {
            s = 0
        }
        return s
    }

    fun cal(count: Int, roll: Int): Int {
        var y = roll
        if (count == 4) {
            y += 1
        }
        return y
    }

    private fun playerDice() {
        var img: Int = R.drawable.dice_30470
        findViewById<ImageView>(R.id.imageView15).setImageResource(img)
        findViewById<ImageView>(R.id.imageView16).setImageResource(img)
        findViewById<ImageView>(R.id.imageView17).setImageResource(img)
        findViewById<ImageView>(R.id.imageView18).setImageResource(img)
        findViewById<ImageView>(R.id.imageView18).setImageResource(img)
    }

    private fun comDice() {
        var img: Int = R.drawable.dice_30470
        findViewById<ImageView>(R.id.imageView20).setImageResource(img)
        findViewById<ImageView>(R.id.imageView22).setImageResource(img)
        findViewById<ImageView>(R.id.imageView23).setImageResource(img)
        findViewById<ImageView>(R.id.imageView24).setImageResource(img)
        findViewById<ImageView>(R.id.imageView25).setImageResource(img)
    }

    private fun diceRoll(randomNo: Int, y: Int) {
        var imageDice: Int
        when (randomNo) {
            1 -> {
                imageDice = R.drawable.d1
            }
            2 -> {
                imageDice = R.drawable.d2
            }
            3 -> {
                imageDice = R.drawable.d3
            }
            4 -> {
                imageDice = R.drawable.d4
            }
            5 -> {
                imageDice = R.drawable.d5
            }
            else -> {
                imageDice = R.drawable.d6
            }
        }
        when (y) {
            1 -> {
                findViewById<ImageView>(R.id.imageView15).setImageResource(imageDice)
            }
            2 -> {
                findViewById<ImageView>(R.id.imageView16).setImageResource(imageDice)
            }
            3 -> {
                findViewById<ImageView>(R.id.imageView17).setImageResource(imageDice)
            }
            4 -> {
                findViewById<ImageView>(R.id.imageView18).setImageResource(imageDice)
            }
            5 -> {
                findViewById<ImageView>(R.id.imageView19).setImageResource(imageDice)
            }
        }
        Toast.makeText(this, "Dice is Rolled", Toast.LENGTH_SHORT).show()
    }

    fun comStrategy(randomNo: Int, y: Int) {
        var imageDice: Int
        when (randomNo) {
            1 -> {
                imageDice = R.drawable.d1
            }
            2 -> {
                imageDice = R.drawable.d2
            }
            3 -> {
                imageDice = R.drawable.d3
            }
            4 -> {
                imageDice = R.drawable.d4
            }
            5 -> {
                imageDice = R.drawable.d5
            }
            else -> {
                imageDice = R.drawable.d6
            }
        }
        when (y) {
            1 -> {
                findViewById<ImageView>(R.id.imageView20).setImageResource(imageDice)
            }
            2 -> {
                findViewById<ImageView>(R.id.imageView22).setImageResource(imageDice)
            }
            3 -> {
                findViewById<ImageView>(R.id.imageView23).setImageResource(imageDice)
            }
            4 -> {
                findViewById<ImageView>(R.id.imageView24).setImageResource(imageDice)
            }
            5 -> {
                findViewById<ImageView>(R.id.imageView25).setImageResource(imageDice)
            }
        }
    }

    fun check(value: Boolean) {
        findViewById<ImageView>(R.id.imageView15).isClickable = value
        findViewById<ImageView>(R.id.imageView16).isClickable = value
        findViewById<ImageView>(R.id.imageView17).isClickable = value
        findViewById<ImageView>(R.id.imageView17).isClickable = value
        findViewById<ImageView>(R.id.imageView19).isClickable = value
    }

    fun reroll(dice: Array<Int>): Array<Int> {
        var img: Int = R.drawable.dice_30470
        findViewById<ImageView>(R.id.imageView15).setOnClickListener {
            findViewById<ImageView>(R.id.imageView15).setImageResource(img)
            dice[0] = 0
        }
        findViewById<ImageView>(R.id.imageView16).setOnClickListener {
            findViewById<ImageView>(R.id.imageView16).setImageResource(img)
            dice[1] = 0
        }
        findViewById<ImageView>(R.id.imageView17).setOnClickListener {
            findViewById<ImageView>(R.id.imageView17).setImageResource(img)
            dice[2] = 0
        }
        findViewById<ImageView>(R.id.imageView18).setOnClickListener {
            findViewById<ImageView>(R.id.imageView18).setImageResource(img)
            dice[3] = 0
        }
        findViewById<ImageView>(R.id.imageView19).setOnClickListener {
            findViewById<ImageView>(R.id.imageView19).setImageResource(img)
            dice[4] = 0
        }
        return dice
    }

    fun win(score: Int, win: Int) {
        var playerScore = score
        var winningPoints = win
        if (playerScore >= winningPoints) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("220210304-Yasiru Jayamadu")
            builder.setMessage("“You win!”")
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("20210304-Yasiru Jayamadu")
            builder.setMessage("“You lose”")
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }
        findViewById<Button>(R.id.troll).isEnabled = false
        findViewById<Button>(R.id.roll).isEnabled = false
        findViewById<Button>(R.id.score).isEnabled = false
    }
}