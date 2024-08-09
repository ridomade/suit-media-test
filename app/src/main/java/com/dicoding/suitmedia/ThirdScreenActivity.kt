package com.dicoding.suitmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.suitmedia.databinding.ActivityThirdScreenBinding

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList()) { user ->

            val resultIntent = Intent().apply {
                putExtra("SELECTED_USER_NAME", "${user.first_name} ${user.last_name}")
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
        binding.recyclerView.adapter = adapter


        binding.swipeRefreshLayout.setOnRefreshListener {
            userViewModel.refreshData()
        }


        userViewModel.loadUsers()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!userViewModel.isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    userViewModel.loadUsers()
                }
            }
        })

        userViewModel.users.observe(this) { users ->
            binding.emptyStateTextView.visibility = if (users.isEmpty()) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
            adapter.updateUsers(users)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}
