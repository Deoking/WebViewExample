package io.github.deoking.webviewexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("https://deoking.github.io")

        urlEditText.setOnEditorActionListener {_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                webView.loadUrl(urlEditText.text.toString())
                true
            }else{
                false
            }

        }

        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //메뉴 리소스를 액티비티의 메뉴로 표시하려면 menuInflater 객체의 inflate 메서드를 호출해서 메뉴 리소스를 지정한다.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("https://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("https://naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("https://www.daum.net")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:010-1234-1234")
                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                sendSMS("010-1234-1234", webView.url)
                return true
            }
            R.id.action_email -> {
                email("text@text.com", "text", webView.url)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                share(webView.url)
                return true
            }
            R.id.action_browser -> {
                browse(webView.url)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
