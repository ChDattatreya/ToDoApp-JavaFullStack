package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // Display tasks on index page
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        return "index";
    }

    // Add new task
    @PostMapping("/tasks")
    public String addTask(@RequestParam String title) {
        service.saveTask(new Task(title, false));
        return "redirect:/";
    }

    // Delete task
    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return "redirect:/";
    }

    // Toggle completion
    @GetMapping("/tasks/toggle/{id}")
    public String toggleTask(@PathVariable Long id) {
        Task task = service.getAllTasks().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(!task.isCompleted());
        service.saveTask(task);
        return "redirect:/";
    }
}

