package com.ericchee.bottomsheetstickyview

import android.app.Dialog
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyBottomSheetDialogFragment: BottomSheetDialogFragment() {

    companion object {
        private val TAG = MyBottomSheetDialogFragment::class.java.simpleName
        private val peekHeight = 500
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let { ctx ->
            // Create dialog
            val dialog = BottomSheetDialog(ctx)

            // Set layout
            dialog.setContentView(R.layout.bottom_sheet_layout)

            // Grab behavior
            val container = dialog.findViewById<ViewGroup>(R.id.design_bottom_sheet)
            val bottomSheetBehavior = BottomSheetBehavior.from(container)
            bottomSheetBehavior.peekHeight = peekHeight

            // Set TextView at the bottom (move it down height of sheet)
            val tvDontScroll = dialog.findViewById<TextView>(R.id.tvDontScroll)
            tvDontScroll?.let { tv ->
                tv.post {
                    tv.translationY = (peekHeight - tv.measuredHeight).toFloat()
                }
            }

            // Listen for Bottom Sheet events
            bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, viewOffset: Float) {
                    Log.i(TAG, "offset: $viewOffset")
                }

                override fun onStateChanged(view: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            Log.i(TAG, "Expanded")

                            // When expanded, Set TextView to constrain to the bottom
                            tvDontScroll?.let { tv ->
                                val container = dialog.findViewById<ConstraintLayout>(R.id.bottomSheetContainer)
                                val constraintSet = ConstraintSet().apply{
                                    clone(dialog.findViewById<ConstraintLayout>(R.id.bottomSheetContainer))
                                }
                                constraintSet.clear(tv.id, ConstraintSet.TOP)
                                constraintSet.connect(tv.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                                constraintSet.applyTo(container)
                                tv.translationY = 0f
                            }
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            Log.i(TAG, "collapsed")
                            // When collapsed again, recalculate to move it to bottom
                            tvDontScroll?.let { tv ->
                                tv.post {
                                    tv.translationY = (peekHeight - tv.measuredHeight).toFloat()
                                }
                            }
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            Log.i(TAG, "Hidden")
                            dismiss()
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            Log.i(TAG, "Half expanded")
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            Log.i(TAG, "Dragging")
                        }
                        else -> Log.i(TAG, "State: $newState")
                    }
                }

            })
            return dialog
        }

        return super.onCreateDialog(savedInstanceState)
    }
}