package com.example.kmpapp

import com.example.feature.auth.ui.AuthViewModel
import com.example.feature.content.ui.ContentListViewModel
import com.example.feature.review.ui.edit.ReviewEditorViewModel
import com.example.feature.review.ui.list.ReviewListViewModel
import com.example.feature.root.RootViewModel
import com.example.feature.topics.ui.TopicViewModel
import com.example.feature.users.ui.info.UserInfoViewModel
import com.example.feature.users.ui.list.UserListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

object ViewModelProvider : KoinComponent {

    fun getRootViewModel(): RootViewModel {
        return getKoin().get()
    }

    fun getAuthViewModel(): AuthViewModel {
        return getKoin().get()
    }

    fun getUserInfoViewModel(userId: String): UserInfoViewModel {
        return getKoin().get { parametersOf(userId) }
    }

    fun getTopicViewModel(): TopicViewModel {
        return getKoin().get()
    }

    fun getUserListViewModel(): UserListViewModel {
        return getKoin().get()
    }

    fun getContentListViewModel(topicId: Long, topicName: String): ContentListViewModel {
        return getKoin().get { parametersOf(topicId, topicName) }
    }

    fun getReviewListViewModel(contentId: Long): ReviewListViewModel {
        return getKoin().get { parametersOf(contentId) }
    }

    fun getReviewEditorViewModel(contentId: Long): ReviewEditorViewModel {
        return getKoin().get() { parametersOf(contentId) }
    }
}