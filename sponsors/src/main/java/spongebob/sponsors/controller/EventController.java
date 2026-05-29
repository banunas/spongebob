package spongebob.sponsors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spongebob.sponsors.model.Event;
import spongebob.sponsors.service.EventService;

// 담당: 
@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("events", eventService.getEventsForCurrentOrg());
        return "event/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("event", new Event());
        return "event/form";
    }

    @PostMapping
    public String create(@ModelAttribute Event event) {
        eventService.createEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "event/detail";
    }
}
