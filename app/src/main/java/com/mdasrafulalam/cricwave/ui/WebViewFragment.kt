package com.mdasrafulalam.cricwave.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.navigation.fragment.findNavController
import com.mdasrafulalam.cricwave.R
import com.mdasrafulalam.cricwave.databinding.FragmentWebViewBinding
import com.mdasrafulalam.cricwave.utils.MyConstants

class WebViewFragment : Fragment() {
    private lateinit var binding: FragmentWebViewBinding
    private lateinit var webview: WebView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val newsUrl = arguments?.getString("url")
        webview = binding.detailsWebView
        if (MyConstants.verifyAvailableNetwork(requireContext())){
            if (newsUrl != null) {
                webview.loadUrl(newsUrl)
            }
            webview.settings.javaScriptEnabled = true
            webview.settings.setSupportZoom(true)
            webview.settings.allowContentAccess = true
        }else{
            MyConstants.noNetwork(requireActivity())
        }
        binding.backbFAB.setOnClickListener {
            findNavController().navigate(R.id.action_webViewFragment_to_homeFragment)
        }
    }
}