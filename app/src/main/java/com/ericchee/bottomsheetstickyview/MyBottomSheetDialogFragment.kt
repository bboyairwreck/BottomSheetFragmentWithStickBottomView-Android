package com.ericchee.bottomsheetstickyview

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout

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

            // Get bottom sheet container
            val contentContainer = dialog.findViewById<ViewGroup>(R.id.design_bottom_sheet)
            val bottomsheetContainer: FrameLayout? = contentContainer?.parent?.parent as FrameLayout
            
            // Create sticky view
            val stickyView = LayoutInflater.from(ctx).inflate(R.layout.sticky_view, bottomsheetContainer, false)

            // Add sticky view to container
            bottomsheetContainer?.addView(stickyView)

            return dialog
        }

        return super.onCreateDialog(savedInstanceState)
    }
}