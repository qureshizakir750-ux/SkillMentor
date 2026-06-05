package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.model.CourseCategory
import com.example.model.defaultCategories
import com.example.network.Content
import com.example.network.GenerateContentRequest
import com.example.network.Part
import com.example.network.RetrofitClient
import com.example.ui.RoadmapStep
import com.example.ui.StepState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UserProgress(
    val lastCompletedCourse: String? = null,
    val currentGoal: String? = "AI Engineer"
)

sealed class MentorState {
    object Idle : MentorState()
    object Loading : MentorState()
    data class Success(val message: String) : MentorState()
    data class Error(val error: String) : MentorState()
}

class AppViewModel : ViewModel() {
    private val _categories = MutableStateFlow<List<CourseCategory>>(defaultCategories)
    val categories: StateFlow<List<CourseCategory>> = _categories.asStateFlow()

    private val _userProgress = MutableStateFlow(UserProgress(lastCompletedCourse = "English Level 1"))
    val userProgress: StateFlow<UserProgress> = _userProgress.asStateFlow()

    private val _mentorState = MutableStateFlow<MentorState>(MentorState.Idle)
    val mentorState: StateFlow<MentorState> = _mentorState.asStateFlow()

    private val _roadmapSteps = MutableStateFlow(generateRoadmap(UserProgress(lastCompletedCourse = "English Level 1")))
    val roadmapSteps: StateFlow<List<RoadmapStep>> = _roadmapSteps.asStateFlow()

    init {
        fetchMentorAdvice()
    }

    private fun generateRoadmap(progress: UserProgress): List<RoadmapStep> {
        return listOf(
            RoadmapStep(progress.lastCompletedCourse ?: "English Level 1", "Foundation", StepState.COMPLETED),
            RoadmapStep("Python Basics", "Next recommended step", StepState.CURRENT),
            RoadmapStep("REST APIs", "Advanced backend skill", StepState.LOCKED),
            RoadmapStep(progress.currentGoal ?: "AI Engineer", "Your professional goal", StepState.GOAL)
        )
    }

    fun completeCourse(course: String) {
        val newProgress = _userProgress.value.copy(lastCompletedCourse = course)
        _userProgress.value = newProgress
        _roadmapSteps.value = generateRoadmap(newProgress)
        fetchMentorAdvice()
    }

    private fun fetchMentorAdvice() {
        if (BuildConfig.GEMINI_API_KEY == "MY_GEMINI_API_KEY") {
            _mentorState.value = MentorState.Success("⚠️ Add your Gemini API Key in the Secrets panel on the left to see your AI Mentor advice! For now: You have completed ${_userProgress.value.lastCompletedCourse}. Next, try Python Basics.")
            return
        }
        _mentorState.value = MentorState.Loading
        viewModelScope.launch {
            val progress = _userProgress.value
            val prompt = """
                The user has completed: ${progress.lastCompletedCourse ?: "nothing yet"}.
                Their goal is: ${progress.currentGoal ?: "general learning"}.
                You are their AI Mentor, English Teacher, Skill Coach, and Career Guide.
                Speak in Hindi but written in English script (Hinglish) or devanagari if you prefer, mixed with English terms, exactly like this style:
                "Tumne English Level 1 pura kar liya hai. Ab Python Basics shuru karo."
                OR "Tumhara lakshya AI Engineer hai. Agla course APIs hai."
                Provide a short, direct, encouraging sentence recommending their next step based on the available courses in an all-in-one skill platform.
            """.trimIndent()

            val response = try {
                withContext(Dispatchers.IO) {
                    val request = GenerateContentRequest(
                        contents = listOf(
                            Content(parts = listOf(Part(text = prompt)))
                        ),
                        systemInstruction = Content(parts = listOf(Part(text = "You are a helpful, direct AI mentor for a skills app.")))
                    )
                    val res = RetrofitClient.service.generateContent(BuildConfig.GEMINI_API_KEY, request)
                    res.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "Agla course shuru karein!"
                }
            } catch (e: Exception) {
                appMentorFallback(progress)
            }
            _mentorState.value = MentorState.Success(response)
        }
    }

    private fun appMentorFallback(progress: UserProgress): String {
        return "Tumne ${progress.lastCompletedCourse} pura kar liya hai. Ab Python Basics shuru karo."
    }
}
