package com.example.trelloclone.activities

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.trelloclone.R
import com.example.trelloclone.databinding.ActivityCardDetailsBinding
import com.example.trelloclone.dialogs.LabelColorListDialog
import com.example.trelloclone.firebase.FireStoreClass
import com.example.trelloclone.models.Board
import com.example.trelloclone.models.Card
import com.example.trelloclone.models.Task
import com.example.trelloclone.utils.Constants

class CardDetailsActivity : BaseActivity() {
    private var binding: ActivityCardDetailsBinding? = null

    private lateinit var mBoard: Board
    private var mTaskListPosition: Int = -1
    private var mCardPosition: Int = -1
    private var mSelectedColor: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardDetailsBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        getIntentData()
        setupActionBar()

        binding?.etNameCardDetails?.setText(mBoard.taskList[mTaskListPosition].cards[mCardPosition].name)
        binding?.etNameCardDetails?.setSelection(binding?.etNameCardDetails?.text.toString().length)

        binding?.btnUpdateCardDetails?.setOnClickListener {
            if (binding?.etNameCardDetails?.text.toString().isNotEmpty()) {
                updateCardDetails()
            }
        }

        binding?.tvSelectLabelColor?.setOnClickListener {
            colorListDialog()
        }
    }

    private fun getIntentData() {
        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoard = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
        if (intent.hasExtra(Constants.CARD_LIST_ITEM_POSITION)) {
            mCardPosition = intent.getIntExtra(Constants.CARD_LIST_ITEM_POSITION, -1)
        }
        if (intent.hasExtra(Constants.TASK_LIST_ITEM_POSITION)) {
            mTaskListPosition = intent.getIntExtra(Constants.TASK_LIST_ITEM_POSITION, -1)
        }
    }

    fun addUpdateTaskListSuccess() {
        hideProgressDialog()

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarCardDetailsActivity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = mBoard.taskList[mTaskListPosition].cards[mCardPosition].name
        }

        binding?.toolbarCardDetailsActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun updateCardDetails() {
        val card = Card(
            binding?.etNameCardDetails?.text.toString(),
            mBoard.taskList[mTaskListPosition].cards[mCardPosition].createdBy,
            mBoard.taskList[mTaskListPosition].cards[mCardPosition].assignedTo,
            mSelectedColor,
        )

        mBoard.taskList[mTaskListPosition].cards[mCardPosition] = card

        showProgressDialog(resources.getString(R.string.please_wait))
        FireStoreClass().addUpdateTaskList(this, mBoard)
    }

    private fun deleteCard() {
        val cardList: ArrayList<Card> = mBoard.taskList[mTaskListPosition].cards

        cardList.removeAt(mCardPosition)

        val taskList: ArrayList<Task> = mBoard.taskList
        taskList.removeAt(taskList.size - 1)

        taskList[mTaskListPosition].cards = cardList
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.menu.menu_delete_card -> {
                deleteCard()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_card, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun setColor() {
        binding?.tvSelectLabelColor?.text = ""
        binding?.tvSelectLabelColor?.setBackgroundColor(Color.parseColor(mSelectedColor))
    }


    private fun colorListDialog() {
        val colorList: ArrayList<String> = colorsList()

        val listDialog = object : LabelColorListDialog(
            this, colorList, resources.getString(R.string.str_select_label_color)
        ) {
            override fun onItemSelected(color: String) {
                setColor()
            }
        }

        listDialog.show()
    }

    private fun colorsList(): ArrayList<String> {
        val colorsList: ArrayList<String> = ArrayList()

        colorsList.add("#43C86F")
        colorsList.add("#0C90F1")
        colorsList.add("#F72400")
        colorsList.add("#7A8089")
        colorsList.add("#D57C1D")
        colorsList.add("#770000")
        colorsList.add("#0022F8")

        return colorsList
    }
}