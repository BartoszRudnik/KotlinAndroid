package com.example.trelloclone.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trelloclone.R
import com.example.trelloclone.adapters.CardMemberListItemsAdapter
import com.example.trelloclone.databinding.ActivityCardDetailsBinding
import com.example.trelloclone.dialogs.LabelColorListDialog
import com.example.trelloclone.dialogs.MembersListDialog
import com.example.trelloclone.firebase.FireStoreClass
import com.example.trelloclone.models.*
import com.example.trelloclone.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CardDetailsActivity : BaseActivity() {
    private var binding: ActivityCardDetailsBinding? = null

    private lateinit var mBoard: Board
    private var mTaskListPosition: Int = -1
    private var mCardPosition: Int = -1
    private var mSelectedColor: String = ""
    private var mSelectedDueDateMilliSeconds: Long = 0

    private lateinit var mMembersDetails: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardDetailsBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        getIntentData()
        setupActionBar()

        binding?.etNameCardDetails?.setText(mBoard.taskList[mTaskListPosition].cards[mCardPosition].name)
        binding?.etNameCardDetails?.setSelection(binding?.etNameCardDetails?.text.toString().length)

        mSelectedDueDateMilliSeconds =
            mBoard.taskList[mTaskListPosition].cards[mCardPosition].dueData
        mSelectedColor = mBoard.taskList[mTaskListPosition].cards[mCardPosition].labelColor

        if (mSelectedDueDateMilliSeconds > 0) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val selectedDate = sdf.format(Date(mSelectedDueDateMilliSeconds))

            binding?.tvSelectDueDate?.text = selectedDate
        }

        if (mSelectedColor.isNotEmpty()) {
            setColor()
        }

        binding?.btnUpdateCardDetails?.setOnClickListener {
            if (binding?.etNameCardDetails?.text.toString().isNotEmpty()) {
                updateCardDetails()
            }
        }

        binding?.tvSelectLabelColor?.setOnClickListener {
            colorListDialog()
        }

        binding?.tvSelectMembers?.setOnClickListener {
            membersListDialog()
        }

        binding?.tvSelectDueDate?.setOnClickListener {
            showDatePicker()
        }

        setupSelectedMembersList()
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
        if (intent.hasExtra(Constants.BOARD_MEMBERS_LIST)) {
            mMembersDetails = intent.getParcelableArrayListExtra(Constants.BOARD_MEMBERS_LIST)!!
        }
    }

    fun addUpdateTaskListSuccess() {
        hideProgressDialog()

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun membersListDialog() {
        var cardAssignedMembersList =
            mBoard.taskList[mTaskListPosition].cards[mCardPosition].assignedTo

        if (cardAssignedMembersList.size > 0) {
            for (i in mMembersDetails.indices) {
                for (j in cardAssignedMembersList) {
                    if (mMembersDetails[i].id == j) {
                        mMembersDetails[i].selected = true
                    }
                }
            }
        } else {
            for (i in mMembersDetails.indices) {
                mMembersDetails[i].selected = false
            }
        }

        val listDialog = object :
            MembersListDialog(this, mMembersDetails, resources.getString(R.string.select_members)) {
            override fun onItemSelected(user: User, action: String) {
                if (action == Constants.SELECT) {
                    if (!mBoard.taskList[mTaskListPosition].cards[mCardPosition].assignedTo.contains(
                            user.id
                        )
                    ) {
                        mBoard.taskList[mTaskListPosition].cards[mCardPosition].assignedTo.add(
                            user.id!!
                        )
                    }
                } else if (action == Constants.UN_SELECT) {
                    mBoard.taskList[mTaskListPosition].cards[mCardPosition].assignedTo.remove(
                        user.id!!
                    )

                    for (i in mMembersDetails.indices) {
                        if (mMembersDetails[i].id == user.id) {
                            mMembersDetails[i].selected = false
                        }
                    }
                }
                setupSelectedMembersList()
            }
        }

        listDialog.show()
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
            mSelectedDueDateMilliSeconds
        )

        val taskList: ArrayList<Task> = mBoard.taskList
        taskList.removeAt(taskList.size - 1)

        mBoard.taskList = taskList
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
            this, colorList, resources.getString(R.string.str_select_label_color), mSelectedColor
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

    private fun setupSelectedMembersList() {
        val cardAssignedMemberList =
            mBoard.taskList[mTaskListPosition].cards[mCardPosition].assignedTo

        val selectedMembersList: ArrayList<SelectedMembers> = ArrayList()

        for (i in mMembersDetails.indices) {
            for (j in cardAssignedMemberList) {
                if (mMembersDetails[i].id == j) {
                    val selectedMember = SelectedMembers(
                        mMembersDetails[i].id!!,
                        mMembersDetails[i].image!!
                    )

                    selectedMembersList.add(selectedMember)
                }
            }
        }

        if (selectedMembersList.size > 0) {
            selectedMembersList.add(SelectedMembers("", ""))
            binding?.tvSelectMembers?.visibility = View.GONE
            binding?.rvSelectedMembersList?.visibility = View.VISIBLE

            binding?.rvSelectedMembersList?.layoutManager = GridLayoutManager(this, 6)

            val adapter = CardMemberListItemsAdapter(this, selectedMembersList, true)

            binding?.rvSelectedMembersList?.adapter = adapter

            adapter.setOnClickListener(object : CardMemberListItemsAdapter.OnClickListener {
                override fun onClick() {
                    membersListDialog()
                }
            })
        } else {
            binding?.tvSelectMembers?.visibility = View.VISIBLE
            binding?.rvSelectedMembersList?.visibility = View.GONE
        }
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val sDayOfMonth = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val sMonthOfYear =
                    if ((monthOfYear + 1) < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"

                val selectedDate = "$sDayOfMonth/$sMonthOfYear/$year"

                binding?.tvSelectDueDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                mSelectedDueDateMilliSeconds = theDate!!.time
            }, year, month, day
        )

        dpd.show()
    }
}