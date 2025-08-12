package com.esfe.Asistencia.controladores;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esfe.Asistencia.Modelos.Grupo;
import com.esfe.Asistencia.Servicios.Interfaces.IGrupoService;

@Controller
@RequestMapping("/grupos")// Define la direccion  URL de  el controller
public class GrupoController {

    @Autowired
    private IGrupoService grupoService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        
        Page<Grupo> grupos = grupoService.buscarTodos(pageable);
        model.addAttribute("grupos", grupos);

        int totalPages = grupos.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "grupo/index";

    }
    ///CREAR
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("grupo", new Grupo());
        model.addAttribute("action", "create");
        return "grupo/mant";
    }
    ///EDITAR
    @GetMapping("/edit/{id}")
    public String edit (@PathVariable Integer id, Model model) {
        Grupo grupo = grupoService.buscarPorId(id).orElseThrow();
        model.addAttribute("grupo", grupo);
        model.addAttribute("action", "edit");
        return "grupo/mant";
    }
    //VER
    @GetMapping("/view/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Grupo grupo = grupoService.buscarPorId(id).orElseThrow();
        model.addAttribute("grupo", grupo);
        model.addAttribute("action", "view");
        return "grupo/view";
    }
    //ELIMINAR Confirmación
    @GetMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Integer id, Model model) {
        Grupo grupo = grupoService.buscarPorId(id).orElseThrow();
        model.addAttribute("grupo", grupo);
        model.addAttribute("action", "delete");
        return "grupo/mant";
    }

    ///PROCESAR POST segun action
    @PostMapping("/create")
    public String saveNuevo(@Valid @ModelAttribute Grupo grupo, BindingResult result, 
                            RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "create");
            return "grupo/mant";
        }
        grupoService.crearOeditar(grupo);
        redirectAttributes.addFlashAttribute("msg", "Grupo creado exitosamente.");
        return "redirect:/grupos";
    }

    @PostMapping("/edit")
    public String saveEditado(@Valid @ModelAttribute Grupo grupo, BindingResult result, 
                            RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "Edit");
            return "grupo/mant";
        }
        grupoService.crearOeditar(grupo);
        redirectAttributes.addFlashAttribute("msg", "Grupo actualizado exitosamente.");
        return "redirect:/grupos";
    }

    @PostMapping("/delete")
    public String deleteGrupo(@ModelAttribute Grupo grupo, RedirectAttributes redirectAttributes) {
        grupoService.eliminarPorId(grupo.getId());
        redirectAttributes.addFlashAttribute("msg", "Grupo eliminado exitosamente.");
        return "redirect:/grupos";
}
}
