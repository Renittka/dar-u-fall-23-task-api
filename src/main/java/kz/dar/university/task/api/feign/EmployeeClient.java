package kz.dar.university.task.api.feign;

import kz.dar.university.task.api.domain.employee.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-core-api")
public interface EmployeeClient {

    @GetMapping("/employee/{id}")
    EmployeeDTO getEmployeeById(@PathVariable String id);

}
