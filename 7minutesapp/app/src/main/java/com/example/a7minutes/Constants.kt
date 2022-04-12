package com.example.a7minutes

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            isCompleted = false,
            isSelected = false
        )
        val abdominalCrunch = ExerciseModel(
            2,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch,
            isCompleted = false,
            isSelected = false
        )
        val highKneesRunningInPlace = ExerciseModel(
            3,
            "High Knees Running in Place",
            R.drawable.ic_high_knees_running_in_place,
            isCompleted = false,
            isSelected = false
        )
        val lunge = ExerciseModel(
            4,
            "Lunge",
            R.drawable.ic_lunge,
            isCompleted = false,
            isSelected = false
        )
        val plank = ExerciseModel(
            5,
            "Plank",
            R.drawable.ic_plank,
            isCompleted = false,
            isSelected = false
        )
        val pushUp = ExerciseModel(
            6,
            "Push Up",
            R.drawable.ic_push_up,
            isCompleted = false,
            isSelected = false
        )
        val pushUpAndRotation = ExerciseModel(
            7,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation,
            isCompleted = false,
            isSelected = false
        )
        val sidePlank = ExerciseModel(
            8,
            "Side Plank",
            R.drawable.ic_side_plank,
            isCompleted = false,
            isSelected = false
        )
        val squat = ExerciseModel(
            9,
            "Squat",
            R.drawable.ic_squat,
            isCompleted = false,
            isSelected = false
        )
        val stepUpOntoChair = ExerciseModel(
            10,
            "Step Up Onto Chair",
            R.drawable.ic_step_up_onto_chair,
            isCompleted = false,
            isSelected = false
        )
        val tricepsDipOnChair = ExerciseModel(
            11,
            "Triceps Dip On Chair",
            R.drawable.ic_triceps_dip_on_chair,
            isCompleted = false,
            isSelected = false
        )
        val wallSit = ExerciseModel(
            12,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            isCompleted = false,
            isSelected = false
        )

        exerciseList.add(abdominalCrunch)
        exerciseList.add(highKneesRunningInPlace)
        exerciseList.add(jumpingJacks)
        exerciseList.add(lunge)
        exerciseList.add(plank)
        exerciseList.add(pushUp)
        exerciseList.add(pushUpAndRotation)
        exerciseList.add(sidePlank)
        exerciseList.add(squat)
        exerciseList.add(stepUpOntoChair)
        exerciseList.add(tricepsDipOnChair)
        exerciseList.add(wallSit)

        return exerciseList
    }
}