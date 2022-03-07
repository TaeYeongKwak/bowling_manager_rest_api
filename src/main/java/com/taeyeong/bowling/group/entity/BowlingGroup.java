package com.taeyeong.bowling.group.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taeyeong.bowling.join.entity.GroupMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bowling_group")
public class BowlingGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gid;
	@Column(nullable = false, length = 100)
	private String title;
	@Column(length = 50)
	private String description;
	@Column(name = "max_member")
	private Integer maxMember;
	
	@OneToMany(mappedBy = "bowlingGroup", cascade = CascadeType.REMOVE)
	@Builder.Default
	@JsonIgnore
	private Set<GroupMember> members = new HashSet<GroupMember>();
}
