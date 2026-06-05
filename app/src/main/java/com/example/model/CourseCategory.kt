package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class CourseCategory(
    val id: String,
    val title: String,
    val courses: List<String>
)

val defaultCategories = listOf(
    CourseCategory("1", "English Learning", listOf("Basic English", "Spoken English", "English Speaking Practice", "English Vocabulary", "English Grammar", "Business English", "Interview English", "Public Speaking", "Typing Practice", "Communication Skills")),
    CourseCategory("2", "AI Courses", listOf("AI Basics", "Prompt Engineering", "Google Gemini", "ChatGPT Mastery", "Claude AI", "AI Agents", "AI Automation", "AI Workflows", "RAG Systems", "MCP Basics", "AI Content Creation", "AI Image Generation", "AI Video Generation", "AI Voice Agents", "AI Coding Assistant", "AI Business Automation")),
    CourseCategory("3", "Programming Courses", listOf("HTML", "CSS", "JavaScript", "TypeScript", "Python", "Java", "C", "C++", "C#", "PHP", "Go", "Rust", "Kotlin", "Swift")),
    CourseCategory("4", "Web Development", listOf("Frontend Development", "Backend Development", "Full Stack Development", "React", "Next.js", "Vue.js", "Angular", "Node.js", "Express.js", "Tailwind CSS")),
    CourseCategory("5", "Mobile App Development", listOf("Android Development", "Flutter", "React Native", "Kotlin", "SwiftUI", "Mobile UI/UX")),
    CourseCategory("6", "Database & APIs", listOf("SQL", "PostgreSQL", "MySQL", "MongoDB", "Firebase", "Supabase", "REST API", "GraphQL", "API Integration")),
    CourseCategory("7", "Cloud & DevOps", listOf("Git", "GitHub", "Docker", "Kubernetes", "Linux", "AWS", "Google Cloud", "Azure", "CI/CD", "DevOps Basics")),
    CourseCategory("8", "Data Science & ML", listOf("Data Analysis", "Excel", "Power BI", "Python for Data Science", "Pandas", "NumPy", "Machine Learning", "Deep Learning", "NLP", "Computer Vision")),
    CourseCategory("9", "Cyber Security", listOf("Cyber Security Basics", "Ethical Hacking", "Network Security", "Web Security", "Bug Bounty", "Penetration Testing")),
    CourseCategory("10", "Social Media Courses", listOf("Instagram Growth", "Instagram Reels", "YouTube Automation", "YouTube Channel Growth", "YouTube SEO", "Facebook Marketing", "X (Twitter) Growth", "LinkedIn Growth", "Personal Branding")),
    CourseCategory("11", "Content Creation", listOf("Video Editing", "CapCut", "Premiere Pro", "After Effects", "Canva", "Thumbnail Design", "Script Writing", "Content Strategy")),
    CourseCategory("12", "Digital Marketing", listOf("SEO", "Blogging", "Affiliate Marketing", "Email Marketing", "Google Ads", "Facebook Ads", "Sales Funnels")),
    CourseCategory("13", "Freelancing", listOf("Fiverr", "Upwork", "Freelancer", "Client Communication", "Proposal Writing", "Pricing Strategy")),
    CourseCategory("14", "Business & Startup", listOf("Startup Basics", "Business Models", "Product Building", "SaaS Business", "AI Startup Building", "Entrepreneurship")),
    CourseCategory("15", "Career & Jobs", listOf("Resume Builder", "LinkedIn Optimization", "Mock Interview", "Portfolio Building", "Remote Jobs", "AI Career Roadmaps")),
    CourseCategory("16", "Automation", listOf("Zapier", "Make.com", "n8n", "AI Automation", "Workflow Automation", "WhatsApp Automation", "Email Automation", "Social Media Automation")),
    CourseCategory("17", "AI App Builder Track", listOf("No-Code Apps", "Low-Code Apps", "AI App Development", "SaaS Building", "MVP Building", "App Deployment"))
)
